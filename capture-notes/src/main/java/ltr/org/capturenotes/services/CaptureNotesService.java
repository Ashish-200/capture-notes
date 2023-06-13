package ltr.org.capturenotes.services;

import ltr.org.capturenotes.beans.CaptureNotesBean;
import ltr.org.capturenotes.beans.CaptureNotesPagingReq;
import ltr.org.capturenotes.beans.CaptureNotesResponse;
import ltr.org.capturenotes.dao.CaptureNotesDao;
import ltr.org.capturenotes.entity.CaptureNotesEntity;
import ltr.org.commonconfig.beans.Pagination;
import ltr.org.commonconfig.beans.PagingResponseBean;
import ltr.org.commonconfig.beans.ServiceResponse;
import ltr.org.commonconfig.exception.UserValidationException;
import ltr.org.commonconfig.utils.PagingConfig;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@CacheConfig(cacheNames="captureNotes")
public class CaptureNotesService {
    @Value("${masters.mimcode.statusId}")
    private String statusIdMimCode;
    private static final Logger logger = LoggerFactory.getLogger(CaptureNotesService.class);
    private final MessageSource messageSource;
    private final CacheManager cacheManager;
    private final CaptureNotesDao captureNotesDao;

    @Autowired
    public CaptureNotesService(MessageSource messageSource, CacheManager cacheManager, CaptureNotesDao captureNotesDao) {
        this.messageSource = messageSource;
        this.cacheManager = cacheManager;
        this.captureNotesDao = captureNotesDao;
    }

    private void clearCache() {
        logger.info("Process of flushing Redis Cache for the application");
        List<String> cacheNames = Arrays.asList("CaptureNotes","CaptureNotesList","CaptureNotes-PagingResponseBean","CaptureNotesServiceMessageSource");
        cacheManager.getCacheNames()
                .parallelStream()
                .filter(cacheNames::contains)
                .toList()
                .forEach(cache -> Objects.requireNonNull(cacheManager.getCache(cache)).clear());
    }
    public ServiceResponse clearRedisCache() {
        clearCache();
        return ServiceResponse.builder()
                              .status(HttpStatus.OK.value())
                              .message(getMessage("message.cache.clear.success"))
                              .timeStamp(LocalDateTime.now())
                              .data(true)
                              .build();
    }
    @Caching(evict = {
            @CacheEvict(value = "CaptureNotes", allEntries = true),
            @CacheEvict(value = "CaptureNotesList", allEntries = true),
            @CacheEvict(value = "CaptureNotes-PagingResponseBean", allEntries = true)})
    public ServiceResponse createNotes(CaptureNotesBean captureNotesBean){
        logger.info("In creating new Notes");

        if(!ObjectUtils.isEmpty(captureNotesBean.getNotesId()))
            throw new UserValidationException(getMessage("validation.create.notesId.null"));

        validations(captureNotesBean);

        CaptureNotesEntity captureNotesEntity = new ModelMapper().map(captureNotesBean,CaptureNotesEntity.class);
        captureNotesEntity.setNotesCreationTime(LocalDateTime.now());
        captureNotesEntity.setIsDeleted("N");

        CaptureNotesEntity captureNotes = this.captureNotesDao.createOrUpdateNotes(captureNotesEntity);

        CaptureNotesResponse response = prepareResponse(captureNotes);

        return ServiceResponse.builder()
                .data(response)
                .status(HttpStatus.CREATED.value())
                .timeStamp(LocalDateTime.now())
                .message(getMessage("message.notes.create.success"))
                .build();
    }
    @Caching(evict = {
            @CacheEvict(value = "CaptureNotes", allEntries = true),
            @CacheEvict(value = "CaptureNotesList", allEntries = true),
            @CacheEvict(value = "CaptureNotes-PagingResponseBean", allEntries = true)})
    public ServiceResponse updateNotes(CaptureNotesBean captureNotesBean) {
        logger.info("In update existing Notes.");

        if(ObjectUtils.isEmpty(captureNotesBean.getNotesId()))
            throw new UserValidationException(getMessage("validation.update.notesId.notNull"));

        Optional<CaptureNotesEntity> optional = this.captureNotesDao.fetchRecordById(captureNotesBean.getNotesId());

        if(optional.isEmpty() || optional.get().getIsDeleted().equalsIgnoreCase("Y"))
            throw new UserValidationException(getMessage("validation.notes.update.notFound"));

        if(StringUtils.isBlank(optional.get().getIsDeleted()))
            optional.get().setIsDeleted("N");

        validations(captureNotesBean);

        CaptureNotesEntity captureNotesEntity = new ModelMapper().map(captureNotesBean,CaptureNotesEntity.class);
        captureNotesEntity.setNotesCreationTime(LocalDateTime.now());
        captureNotesEntity.setIsDeleted("N");

        CaptureNotesEntity captureNotes = this.captureNotesDao.createOrUpdateNotes(captureNotesEntity);

        CaptureNotesResponse response = prepareResponse(captureNotes);

        return ServiceResponse.builder()
                .data(response)
                .status(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .message(getMessage("message.notes.update.success"))
                .build();
    }
    @Cacheable(value = "CaptureNotesList",keyGenerator = "customKeyGen")
    public ServiceResponse fetchAllNotes(CaptureNotesBean captureNotesBean){
        logger.info("In Fetch all notes");

        CaptureNotesEntity captureNotes;
        if(ObjectUtils.isEmpty(captureNotesBean))
            captureNotes= new CaptureNotesEntity();
        else
            captureNotes = new ModelMapper().map(captureNotesBean, CaptureNotesEntity.class);

        List<CaptureNotesEntity> captureNotesEntityList = this.captureNotesDao.fetchAllRecords(captureNotes);

        if(CollectionUtils.isEmpty(captureNotesEntityList))
            throw new UserValidationException(getMessage("validation.notes.notFound"));

        List<CaptureNotesResponse> responseList = new ArrayList<>();

        for(CaptureNotesEntity captureNotesEntity : captureNotesEntityList){
            if(StringUtils.isBlank(captureNotesEntity.getIsDeleted()) || captureNotesEntity.getIsDeleted().equalsIgnoreCase("N")) {
                CaptureNotesResponse response = prepareResponse(captureNotesEntity);
                responseList.add(response);
            }
        }

        return ServiceResponse.builder()
                .data(responseList)
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message(getMessage("message.notes.fetch.success"))
                .build();
    }
    @Cacheable(value="CaptureNotes-PagingResponseBean",keyGenerator = "customKeyGen")
    public ServiceResponse fetchNotesUsingPaging(CaptureNotesPagingReq captureNotesPagingReq){

        logger.info("In fetch Notes using filters, paging and sorting");

        Pageable pageable= PagingConfig.getPageableConfig(
                captureNotesPagingReq.getPageNo(),
                captureNotesPagingReq.getPageSize(),
                captureNotesPagingReq.getSortingFieldWithOrder(),
                "notesId");

        CaptureNotesEntity captureNotesEntity;
        if(ObjectUtils.isEmpty(captureNotesPagingReq.getCaptureNotesBean())){
            captureNotesEntity = new CaptureNotesEntity();
        }else{
            captureNotesEntity = new ModelMapper().map(captureNotesPagingReq.getCaptureNotesBean(), CaptureNotesEntity.class);
        }
        Page<CaptureNotesEntity> page = this.captureNotesDao.fetchRecordUsingPaging(pageable,captureNotesEntity);

        if(CollectionUtils.isEmpty(page.getContent()))
            throw new UserValidationException(getMessage("validation.notes.notFound"));

        Pagination pagination = PagingConfig.getPagination(page);
        List<CaptureNotesResponse> responseList = new ArrayList<>();

        for(CaptureNotesEntity entity : page.getContent()){
            if(StringUtils.isBlank(entity.getIsDeleted()) || entity.getIsDeleted().equalsIgnoreCase("N")) {
                CaptureNotesResponse response = prepareResponse(entity);
                responseList.add(response);
            }
        }

        PagingResponseBean responseBean=PagingResponseBean.builder()
                .pagination(pagination)
                .list(responseList)
                .build();

        return ServiceResponse.builder()
                .data(responseBean)
                .status(HttpStatus.OK.value())
                .message(getMessage("message.notes.pages.success"))
                .timeStamp(LocalDateTime.now())
                .build();
    }
    public void validations(CaptureNotesBean captureNotesBean) {
        if(!captureNotesDao.isValidFeatureId(captureNotesBean.getFeatureId()))
            throw new UserValidationException(getMessage("validation.featureId.invalid"));

        if(!captureNotesDao.isValidLmsId(captureNotesBean.getLmsId()))
            throw new UserValidationException(getMessage("validation.lmsId.invalid"));
    }
    private CaptureNotesResponse prepareResponse(CaptureNotesEntity captureNotes) {
        CaptureNotesResponse response = new ModelMapper().map(captureNotes,CaptureNotesResponse.class);
        response.setFeatureName(this.captureNotesDao.getFeatureName(response.getFeatureId()));
        response.setStatusDesc(this.captureNotesDao.getMimCodeValueDesc(response.getStatusId(),statusIdMimCode));
        response.setLoanNumber(this.captureNotesDao.getLoanNumber(captureNotes.getLmsId()));
        return response;
    }
    public String getMessage(String code) {
        return messageSource.getMessage(code,null, LocaleContextHolder.getLocale());
    }
}