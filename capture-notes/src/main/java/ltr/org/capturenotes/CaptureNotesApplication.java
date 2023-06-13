package ltr.org.capturenotes;

import ltr.org.commonconfig.service.CommonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static ltr.org.capturenotes.utils.Constants.PACKAGE_NAME;

@EnableCaching
@EntityScan(basePackages = PACKAGE_NAME)
@ComponentScan(basePackages = PACKAGE_NAME)
@SpringBootApplication(scanBasePackages = PACKAGE_NAME)
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class, basePackages = PACKAGE_NAME)
public class CaptureNotesApplication {
    public static void main(String[] args) {
        CommonService.configSetup();
        SpringApplication.run(CaptureNotesApplication.class, args);
    }
}
