package ltr.org.capturenotes.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureNotesPagingReq implements Serializable {
    private CaptureNotesBean captureNotesBean;
    @Min(value = 0,message ="{validation.pageNo.min}")
    private Integer pageNo;
    @Min(value = 1,message = "{validation.pageSize.min}")
    private Integer pageSize;
    private String [] sortingFieldWithOrder;
}
