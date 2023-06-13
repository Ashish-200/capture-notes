package ltr.org.capturenotes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ltr.org.commonconfig.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "LOAN_DETAILS_TXN" , schema = "LOAN_TRANSACTIONS")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDetailsTxnEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "LMS_ID")
    private Long lmsId;
    @Column(name = "LOS_ID")
    private Long losId;
    @Column(name = "APPLICATION_FACILITY_ID")
    private Long applicationFacilityId;
    @Column(name = "LOAN_NO")
    private String loanNo;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "SUB_PRODUCT_ID")
    private Long subProductId;
    @Column(name = "SCHEME_ID")
    private Long schemeId;
    @Column(name = "PROGRAM_ID")
    private Long programId;
    @Column(name = "BRANCH_ID")
    private Long branchId;
    @Column(name = "SERVICING_BRANCH_ID")
    private Long servicingBranchId;
    @Column(name = "LOAN_AMOUNT")
    private BigDecimal loanAmount;
    @Column(name = "COLLATERAL_VALUE")
    private BigDecimal collateralValue;
    @Column(name = "LOAN_LTV")
    private BigDecimal loanLtv;
    @Column(name = "LOAN_MATURITY_DATE")
    private Date loanMaturityDate;
    @Column(name = "LOAN_PURPOSE")
    private String loanPurpose;
    @Column(name = "PRODUCT_TYPE")
    private String productType;
    @Column(name = "REVOLVING_TYPE")
    private String revolvingType;
    @Column(name = "PRODUCT_NATURE")
    private String productNature;
    @Column(name = "ROI_TYPE")
    private String roiType;
    @Column(name = "ROI_METHOD")
    private String roiMethod;
    @Column(name = "FUNDING_SOURCE_TYPE")
    private String fundingSourceType;
    @Column(name = "FUNDING_SOURCE_RATE")
    private Double fundingSourceRate;
    @Column(name = "MARKUP")
    private BigDecimal markup;
    @Column(name = "INTEREST_RATE")
    private BigDecimal interestRate;
    @Column(name = "FLAT_RATE")
    private BigDecimal flatRate;
    @Column(name = "BUSINESS_RATE")
    private BigDecimal businessRate;
    @Column(name = "DUE_DAY")
    private Long dueDay;
    @Column(name = "LOAN_TENURE")
    private Long loanTenure;
    @Column(name = "LOAN_NO_INSTALMENT")
    private Long loanNoInstalment;
    @Column(name = "INSTALMENT_INT_START_DATE")
    private Date instalmentIntStartDate;
    @Column(name = "INTEREST_START_DATE")
    private Date interestStartDate;
    @Column(name = "FIRST_DUE_DATE")
    private Date firstDueDate;
    @Column(name = "LOAN_FREQUENCY_TYPE")
    private String loanFrequencyType;
    @Column(name = "LOAN_FREQUENCY")
    private String loanFrequency;
    @Column(name = "INTEREST_FREQUENCY")
    private String interestFrequency;
    @Column(name = "PRINCIPAL_FREQUENCY")
    private String principalFrequency;
    @Column(name = "INT_COMPOUNDING_FREQUENCY")
    private String intCompoundingFrequency;
    @Column(name = "AMORT_TYPE")
    private String amortType;
    @Column(name = "ADVANCE_INSTALMENT_TYPE")
    private String advanceInstalmentType;
    @Column(name = "NO_OF_ADVANCE_INSTALMENT")
    private Long noOfAdvanceInstalment;
    @Column(name = "VALUE_DATE")
    private Date valueDate;
    @Column(name = "IS_DELETED",length = 1)
    private String isDeleted;
}
