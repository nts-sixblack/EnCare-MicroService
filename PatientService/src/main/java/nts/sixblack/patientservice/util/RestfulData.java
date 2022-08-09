package nts.sixblack.patientservice.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import nts.sixblack.patientservice.form.AppointmentForm;
import nts.sixblack.patientservice.form.FeedbackForm;
import nts.sixblack.patientservice.form.NewPasswordForm;
import nts.sixblack.patientservice.response.AccountResponse;
import nts.sixblack.patientservice.response.ResponseObject;
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

    public AccountResponse informationAccount(long accountId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://gateway/account/information/"+accountId, ResponseObject.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return null;
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            return gson.fromJson(jsonObject, AccountResponse.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public long getAccountIdFromToken() {
        try {
            ResponseEntity<Long> response = restTemplate.getForEntity("http://gateway/account/get",Long.class);
            System.out.println("accountId in token: "+response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.out.println("Fail in here: "+e);
            return 0;
        }
    }

    public boolean changePassword(NewPasswordForm newPasswordForm) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity("http://gateway/account/changePassword", newPasswordForm, ResponseObject.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return false;
            }
            return true;
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

    public boolean newAppointment(AppointmentForm appointmentForm) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity("http://appointment-service/appointment/new", appointmentForm, ResponseObject.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cancelAppointment(long userId, long appointmentId) {
        restTemplate.getForEntity("http://appointment-service/appointment/cancel?userId="+userId+"&appointmentId="+appointmentId, null);
    }

    public ResponseObject historyAppointment(long userId, int page, int value) {
        ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/appointment/history?userId="+userId+"&page="+page+"&status="+value, ResponseObject.class);
        return response.getBody();
    }

    public boolean existFeedback(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/feedback/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean newFeedback(FeedbackForm feedbackForm) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity("http://appointment-service/feedback/new", feedbackForm, ResponseObject.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

