package ltr.org.capturenotes.entity;

import lombok.*;
import ltr.org.commonconfig.entity.BaseEntity;


import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "SYSTEM_FEATURES_M",schema = "ACCESS_MANAGEMENT")
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SystemFeaturesM extends BaseEntity implements Serializable {
    @Id
    @Column(name = "FEATURE_ID")
    private Long featureId;
    @Column(name = "FEATURE_NAME")
    private String featureName;
    @Column(name = "MASTER_FEATURE_ID")
    private Long masterFeatureId;
    @Column(name = "SEGMENT_ID")
    private Long segmentId;
    @Column(name = "ACCOUNTING_FEATURE")
    private String accountingFeature;
    @Column(name = "FEATURE_FRONTEND")
    private String featureFrontEnd;
    @Column(name = "DUE_CREATION")
    private String dueCreation;
    @Column(name = "IS_DELETED",length = 1)
    private String isDeleted;
}
