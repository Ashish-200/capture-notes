package ltr.org.capturenotes.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@Configuration
public class AuditLogDispatcherServlet extends DispatcherServlet {
    @Override
    protected void doDispatch(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        try {
            super.doDispatch(request, response);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            log(request, response, System.currentTimeMillis()-startTime);
            try{
                updateResponse(response);
            }catch (Exception exception){
                log.error(exception.getMessage());
            }
        }
    }
    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, long processTime) {
        LogMessage logMessage = new LogMessage();
        logMessage.setHttpStatus((responseToCache.getStatus()));
        logMessage.setHttpMethod(requestToCache.getMethod());
        logMessage.setPath(requestToCache.getRequestURI());
        logMessage.setClientIp(requestToCache.getRemoteAddr());
        logMessage.setTimeTaken(processTime);
        logMessage.setResponse(getResponsePayload(responseToCache));
        log.info(logMessage.toString());
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if(responseWrapper!=null)
            responseWrapper.copyBodyToResponse();
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration() {
        return new ServletRegistrationBean<>(dispatcherServlet());
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new AuditLogDispatcherServlet();
    }
}