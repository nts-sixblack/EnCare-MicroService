package nts.sixblack.zuulservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private long doctorId;
    private float rating;
    private long countRating;

    private AccountResponse accountResponse;
    private CategoryResponse categoryResponse;
    private HospitalResponse hospitalResponse;
}
