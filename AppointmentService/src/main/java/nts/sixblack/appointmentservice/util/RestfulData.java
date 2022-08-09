package nts.sixblack.appointmentservice.util;

import nts.sixblack.appointmentservice.form.UpdateRatingForm;
import nts.sixblack.appointmentservice.response.ResponseObject;
import nts.sixblack.appointmentservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestfulData {

    @Autowired
    RestTemplate restTemplate;

    public boolean existPatient(long userId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/exist/"+userId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean existData(ResponseEntity<ResponseObject> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        return (Boolean) response.getBody().getData();
    }

    public UserResponse informationPatient(long userId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/information/"+userId, ResponseObject.class);
            return (UserResponse) response.getBody().getData();
        } catch (Exception e) {
            return null;
        }
    }

    public UserResponse informationPatientByAccountUserId(long accountUserId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/accountId/"+accountUserId, ResponseObject.class);
            return (UserResponse) response.getBody().getData();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existAccount(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://gateway/account/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existDoctor(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/doctor/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public void updateRatingDoctor(UpdateRatingForm updateRatingForm) {
        restTemplate.postForEntity("http://doctor-service/doctor/updateRating", updateRatingForm, null);
    }

    public boolean existCategory(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/category/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existHospital(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/hospital/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existAppointment(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/appointment/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existFeedback(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/feedback/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

}

