package nts.sixblack.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.sixblack.userservice.form.mapbox.Distance;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private long doctorId;
    private float rating;
    private long countRating;
    private Distance distance;

    private AccountResponse accountResponse;
    private CategoryResponse categoryResponse;
    private HospitalResponse hospitalResponse;
}
