package nts.sixblack.patientservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackForm {
    private long accountUserId;
    @NotNull
    @Min(0)
    private long appointmentId;
    @NotNull
    @Min(1)
    @Max(5)
    private float rating;
    @NotBlank
    @NotNull
    private String comment;
    private long userId;
}
