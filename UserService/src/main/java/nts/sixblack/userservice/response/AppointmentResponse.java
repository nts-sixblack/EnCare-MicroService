package nts.sixblack.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private long appointmentId;
    private String symptoms;
    private String description;
    private int time;

    private String day;
    private String createDate;
    private long doctorId;
    private long userId;
    private DoctorResponse doctorResponse;
    private UserResponse userResponse;

    private String status;
}
