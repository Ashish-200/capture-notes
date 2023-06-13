package ltr.org.capturenotes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltr.org.capturenotes.beans.CaptureNotesBean;
import ltr.org.capturenotes.beans.CaptureNotesPagingReq;
import ltr.org.capturenotes.services.CaptureNotesService;
import ltr.org.commonconfig.beans.ServiceResponse;
import ltr.org.commonconfig.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static ltr.org.capturenotes.utils.Constants.TENANT_ID_KEY;
import static ltr.org.capturenotes.utils.Constants.TRACE_ID_KEY;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/v1")
@Api(tags = "Capture Notes Service", value = "Capture Notes Service version 1")
public class CaptureNotesController {
    private static final Logger logger = LoggerFactory.getLogger(CaptureNotesController.class);
    private final CaptureNotesService captureNotesService;
    @Autowired
    public CaptureNotesController(CaptureNotesService captureNotesService) {
        this.captureNotesService = captureNotesService;
    }

    @ApiOperation(value = "Clear Redis Cache for Notes",
                  httpMethod = "GET",
                  produces = MediaType.APPLICATION_JSON_VALUE,
                  consumes = MediaType.APPLICATION_JSON_VALUE,
                  response = ServiceResponse.class)
    @GetMapping(value = "/clearCache",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> clearRedisCache(@RequestHeader (value = TRACE_ID_KEY) String traceId,
                                                           @RequestHeader (value = TENANT_ID_KEY) Integer tenantId){
        logger.info("Request received to clear cache.");
        ServiceResponse response = this.captureNotesService.clearRedisCache();
        response.setTraceId(traceId);
        response.setTenantId(tenantId);
        return ResponseEntity.status(response.getStatus())
                             .body(response);
    }
    @ApiOperation(value = "Create New Notes",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = ServiceResponse.class)
    @PostMapping(value = "/createNotes",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> createSetOffReversalTxn(@Valid @RequestBody CaptureNotesBean captureNotesBean,
                                                                   @RequestHeader(value = TRACE_ID_KEY) String traceId,
                                                                   @RequestHeader(value = TENANT_ID_KEY) Integer tenantId){
        logger.info("Request received to create notes {}", captureNotesBean);
        ServiceResponse response = this.captureNotesService.createNotes(captureNotesBean);
        response.setTraceId(traceId);
        response.setTenantId(tenantId);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
    @ApiOperation(value = "Update Existing Notes",
            httpMethod = "PUT",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            response = ServiceResponse.class)
    @PutMapping(value = "/updateNotes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> updateSetOffReversalTxn(@Valid @RequestBody CaptureNotesBean captureNotesBean,
                                                                  @RequestHeader(value = TRACE_ID_KEY) String traceId,
                                                                  @RequestHeader(value = TENANT_ID_KEY) Integer tenantId){
        logger.info("Request received to update Notes {}",captureNotesBean);
        ServiceResponse response=this.captureNotesService.updateNotes(captureNotesBean);
        response.setTenantId(tenantId);
        response.setTraceId(traceId);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
    @ApiOperation(value = "Fetch all Notes",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = ServiceResponse.class)
    @PostMapping(value = "/fetchNotes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> fetchAllNotes(@RequestHeader(value = TRACE_ID_KEY) String traceId,
                                                         @RequestHeader(value = TENANT_ID_KEY) Integer tenantId,
                                                         @RequestBody CaptureNotesBean captureNotesBean){
        logger.info("Request Received to fetch all Notes {}",captureNotesBean);
        ServiceResponse response=this.captureNotesService.fetchAllNotes(captureNotesBean);
        response.setTraceId(traceId);
        response.setTenantId(tenantId);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
    @ApiOperation(value = "Fetch Notes using pagination,filters and sorting",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            response = ServiceResponse.class)
    @PostMapping(value = "/fetchNotesUsingPaging",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> fetchNotesUsingPaging(@RequestHeader(value = TRACE_ID_KEY) String traceId,
                                                                            @RequestHeader(value = TENANT_ID_KEY) Integer tenantId,
                                                                            @Valid @RequestBody CaptureNotesPagingReq captureNotesPagingReq){
        logger.info("Request received to fetch Notes using pagination {}", captureNotesPagingReq);
        ServiceResponse response = this.captureNotesService.fetchNotesUsingPaging(captureNotesPagingReq);
        response.setTraceId(traceId);
        response.setTenantId(tenantId);
        return ResponseEntity.status(response.getStatus())
                             .body(response);
    }
    @ApiOperation(value = "Information related with Notes",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = ServiceResponse.class)
    @GetMapping(value="/help",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> fetchHelp(@RequestHeader(value = TRACE_ID_KEY) String traceId,
                                                     @RequestHeader(value = TENANT_ID_KEY) Integer tenantId){
        logger.info("Request received in Help section");
        String helpUrl=System.getProperty("spring.help.url");
        ServiceResponse response=ServiceResponse.builder()
                .data(CommonService.fetchDocumentFromCouchbase(helpUrl))
                .timeStamp(LocalDateTime.now())
                .traceId(traceId)
                .tenantId(tenantId)
                .status(HttpStatus.OK.value())
                .message(captureNotesService.getMessage("message.success"))
                .build();
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}
