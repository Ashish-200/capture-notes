package ltr.org.capturenotes.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ltr.org.commonconfig.beans.BaseBean;

import java.io.Serializable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureNotesResponse extends BaseBean implements Serializable {
    private Long notesId;
    private Long featureId;
    private String loanNumber;
    private String featureName;
    private String notes;
    private LocalDateTime notesCreationTime;
    private String statusDesc;
    private String isDeleted;
}
