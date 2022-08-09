package nts.sixblack.doctorservice.util;

import nts.sixblack.doctorservice.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestfulData {

    @Autowired
    RestTemplate restTemplate;

    public boolean existPatient(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/exist/"+doctorId, ResponseObject.class);
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


