package nts.sixblack.zuulservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponse {
    private long hospitalId;
    private String description;
    private double latMap;
    private double longMap;
    private long rating;
    private long countRating;
    private String address;
    private String name;

    public HospitalResponse(long hospitalId) {
        this.hospitalId = hospitalId;
    }
}
