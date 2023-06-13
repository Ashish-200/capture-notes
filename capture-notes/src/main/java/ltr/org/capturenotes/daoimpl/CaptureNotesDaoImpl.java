package ltr.org.capturenotes.daoimpl;

import ltr.org.capturenotes.dao.CaptureNotesDao;
import ltr.org.capturenotes.entity.CaptureNotesEntity;
import ltr.org.capturenotes.entity.LoanDetailsTxnEntity;
import ltr.org.capturenotes.entity.SystemFeaturesM;
import ltr.org.capturenotes.repository.ICaptureNotesRepository;
import ltr.org.capturenotes.repository.ILoanDetailsTxnRepository;
import ltr.org.capturenotes.repository.ISystemFeaturesMRepository;
import ltr.org.commonconfig.entity.GenericMaster;
import ltr.org.commonconfig.repository.GenericMasterRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CaptureNotesDaoImpl implements CaptureNotesDao {
    private final ICaptureNotesRepository captureNotesRepository;
    private final ISystemFeaturesMRepository systemFeaturesMRepository;
    private final GenericMasterRepository genericMasterRepository;
    private final ILoanDetailsTxnRepository loanDetailsTxnRepository;
    public CaptureNotesDaoImpl(ICaptureNotesRepository captureNotesRepository, ISystemFeaturesMRepository systemFeaturesMRepository, GenericMasterRepository genericMasterRepository, ILoanDetailsTxnRepository loanDetailsTxnRepository) {
        this.captureNotesRepository = captureNotesRepository;
        this.systemFeaturesMRepository = systemFeaturesMRepository;
        this.genericMasterRepository = genericMasterRepository;
        this.loanDetailsTxnRepository = loanDetailsTxnRepository;
    }

    @Override
    public CaptureNotesEntity createOrUpdateNotes(CaptureNotesEntity captureNotesEntity) {
        return captureNotesRepository.save(captureNotesEntity);
    }

    @Override
    public Optional<CaptureNotesEntity> fetchRecordById(Long notesId) {
        return captureNotesRepository.findById(notesId);
    }
    @Override
    public List<CaptureNotesEntity> fetchAllRecords(CaptureNotesEntity captureNotesEntity) {
            Example<CaptureNotesEntity> masterExample=Example.of(captureNotesEntity, ExampleMatcher.matchingAll().withIgnoreCase());
            return captureNotesRepository.findAll(masterExample);
    }

    @Override
    public Page<CaptureNotesEntity> fetchRecordUsingPaging(Pageable pageable, CaptureNotesEntity captureNotesEntity) {
        Example<CaptureNotesEntity> captureNotesEntityExample = Example.of(captureNotesEntity, ExampleMatcher.matchingAll().withIgnoreCase());
        return captureNotesRepository.findAll(captureNotesEntityExample,pageable);
    }

    @Override
    public String getMimCodeValueDesc(String mimCodeValue, String mimCode) {
        Optional<GenericMaster> genericMaster = genericMasterRepository.findByMimCodeValueIgnoreCaseAndMimCodeIgnoreCase(mimCodeValue,mimCode);
        return genericMaster.isPresent() ? genericMaster.get().getMimCodeValueDescription() : "";
    }
    @Override
    public boolean isValidFeatureId(Long featureId) {
        return systemFeaturesMRepository.isValidFeatureId(featureId);
    }
    @Override
    public String getFeatureName(Long featureId) {
        Optional<SystemFeaturesM> systemFeatureEntity = systemFeaturesMRepository.getFeatureDetailsById(featureId);
        return systemFeatureEntity.isPresent() ? systemFeatureEntity.get().getFeatureName() : "";
    }

    @Override
    public boolean isValidLmsId(Long lmsId) {
        return loanDetailsTxnRepository.isValidLmsId(lmsId);
    }

    @Override
    public String getLoanNumber(Long lmsId) {
        Optional<LoanDetailsTxnEntity> loanDetailsTxn = loanDetailsTxnRepository.getLoanDetails(lmsId);
        return loanDetailsTxn.isPresent() ? loanDetailsTxn.get().getLoanNo() : "";
    }
}
