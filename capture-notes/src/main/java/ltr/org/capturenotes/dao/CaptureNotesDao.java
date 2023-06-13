package ltr.org.capturenotes.dao;

import ltr.org.capturenotes.entity.CaptureNotesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CaptureNotesDao {
    CaptureNotesEntity createOrUpdateNotes(CaptureNotesEntity captureNotesEntity);
    Optional<CaptureNotesEntity> fetchRecordById(Long notesId);
    List<CaptureNotesEntity> fetchAllRecords(CaptureNotesEntity captureNotesEntity);
    Page<CaptureNotesEntity> fetchRecordUsingPaging(Pageable pageable, CaptureNotesEntity captureNotesEntity);
    String getMimCodeValueDesc(String mimCodeValue, String mimCode);
    boolean isValidFeatureId(Long featureId);
    String getFeatureName(Long featureId);
    boolean isValidLmsId(Long lmsId);
    String getLoanNumber(Long lmsId);
}
