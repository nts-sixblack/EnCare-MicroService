package nts.sixblack.appointmentservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingForm {
    private long doctorId;
    private float rating;
}
