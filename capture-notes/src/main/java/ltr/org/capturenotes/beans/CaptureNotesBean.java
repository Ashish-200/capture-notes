package ltr.org.capturenotes.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ltr.org.commonconfig.beans.BaseBean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureNotesBean extends BaseBean implements Serializable {
    private Long notesId;
    @NotNull(message = "{message.featureId.notNull}")
    private Long featureId;
    @NotNull(message = "{message.lmsId.notNull}")
    private Long lmsId;
    @NotBlank(message = "{message.notes.notBlank}")
    private String notes;
}
