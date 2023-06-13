package ltr.org.capturenotes.repository;

import ltr.org.capturenotes.entity.SystemFeaturesM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ISystemFeaturesMRepository extends JpaRepository<SystemFeaturesM,Long> {
    @Query(value = "select count(1) = 1 from SystemFeaturesM where featureId = (:featureId) and upper(statusId) = 'A' and (upper(isDeleted) = 'N' or isDeleted is null)")
    boolean isValidFeatureId(Long featureId);
    @Query(value = "from SystemFeaturesM where featureId = (:featureId) and upper(statusId) = 'A' and (upper(isDeleted) = 'N' or isDeleted is null)")
    Optional<SystemFeaturesM> getFeatureDetailsById(Long featureId);
}