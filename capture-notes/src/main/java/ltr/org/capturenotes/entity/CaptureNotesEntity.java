package ltr.org.capturenotes.entity;

import lombok.*;
import ltr.org.commonconfig.entity.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Audited
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "NOTES",schema = "LMS_MASTERS")
@AllArgsConstructor
@NoArgsConstructor
public class CaptureNotesEntity extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTES_SEQ")
    @SequenceGenerator(name = "NOTES_SEQ", sequenceName = "NOTES_SEQ", initialValue = 1,allocationSize = 1,schema = "LMS_MASTERS")
    @Column(name = "NOTES_ID", nullable = false, updatable = false)
    private Long notesId;
    @Column(name = "FEATURE_ID")
    private Long featureId;
    @Column(name = "LMS_ID")
    private Long lmsId;
    @Column(name = "NOTES",length = 4000)
    private String notes;
    @Column(name = "NOTES_CREATION_TIME")
    private LocalDateTime notesCreationTime;
    @Column(name = "IS_DELETED",length = 1)
    private String isDeleted;
}
