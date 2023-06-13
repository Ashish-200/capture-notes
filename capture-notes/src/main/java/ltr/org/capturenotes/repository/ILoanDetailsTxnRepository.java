package ltr.org.capturenotes.repository;

import ltr.org.capturenotes.entity.LoanDetailsTxnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ILoanDetailsTxnRepository extends JpaRepository<LoanDetailsTxnEntity,Long> {
    @Query(value = "select count(1) >= 1 from LoanDetailsTxnEntity where lmsId = (:lmsId) and upper(statusId) = 'A' and (upper(isDeleted) = 'N' or isDeleted is null) ")
    boolean isValidLmsId(Long lmsId);

    @Query(value = " from LoanDetailsTxnEntity where lmsId = (:lmsId) and upper(statusId) = 'A' and (upper(isDeleted) = 'N' or isDeleted is null) ")
    Optional<LoanDetailsTxnEntity> getLoanDetails(Long lmsId);
}
