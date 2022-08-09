package nts.sixblack.userservice.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import nts.sixblack.userservice.form.FreeTimeForm;
import nts.sixblack.userservice.form.InformationForm;
import nts.sixblack.userservice.form.NewPasswordForm;
import nts.sixblack.userservice.response.*;
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
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/patient/exist/"+userId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public UserResponse informationPatient(long userId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://patient-service/patient/information/"+userId, ResponseObject.class);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            UserResponse userResponse = gson.fromJson(jsonObject, UserResponse.class);
            userResponse.setAccountResponse(informationAccount(userResponse.getAccountResponse().getAccountId()));
            return userResponse;
        } catch (Exception e) {
            System.out.println(e);
            return null;
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
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            return gson.fromJson(jsonObject, AccountResponse.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean updateInformation(InformationForm informationForm) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity("http://gateway/account/update", informationForm, ResponseObject.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
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

    public DoctorResponse informationDoctor(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/doctor/information/"+doctorId, ResponseObject.class);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            DoctorResponse doctorResponse = gson.fromJson(jsonObject, DoctorResponse.class);
            doctorResponse.setAccountResponse(informationAccount(doctorResponse.getAccountResponse().getAccountId()));
            doctorResponse.setCategoryResponse(informationCategory(doctorResponse.getCategoryResponse().getCategoryId()));
            doctorResponse.setHospitalResponse(informationHospital(doctorResponse.getHospitalResponse().getHospitalId()));
            return doctorResponse;
        } catch (Exception e) {
            return null;
        }
    }

    public Object listDoctorOfCategory(long categoryId, int page, int rating, double lon, double lat) {
        try {
            String url = "http://doctor-service/doctor/listDoctor?categoryId="+categoryId+"&lon="+lon+"&lat="+lat+"&page="+page+"&rating="+rating;
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity(url, ResponseObject.class);
            return response.getBody().getData();
        } catch (Exception e) {
            return null;
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

    public CategoryResponse informationCategory(long categoryId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/category/information/"+categoryId, ResponseObject.class);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            return gson.fromJson(jsonObject, CategoryResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Object listCategory() {
        ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/category/list", ResponseObject.class);
        return response.getBody().getData();
    }

    public boolean existHospital(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/hospital/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

    public HospitalResponse informationHospital(long categoryId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/hospital/information/"+categoryId, ResponseObject.class);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            return gson.fromJson(jsonObject, HospitalResponse.class);
        } catch (Exception e) {
            return null;
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

    public Object informationAppointment(long appointmentId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/appointment/information/"+appointmentId, ResponseObject.class);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.toJsonTree(response.getBody().getData()).getAsJsonObject();
            AppointmentResponse appointmentResponse = gson.fromJson(jsonObject, AppointmentResponse.class);
            System.out.println("user: doctorId: "+appointmentResponse.getDoctorId());
            System.out.println("user: userId: "+appointmentResponse.getUserId());
            appointmentResponse.setDoctorResponse(informationDoctor(appointmentResponse.getDoctorId()));
            appointmentResponse.setUserResponse(informationPatient(appointmentResponse.getUserId()));
//            appointmentResponse.setUserResponse();
            return appointmentResponse;
        } catch (Exception e) {
            return null;
        }
    }

    public Object listFreetime(FreeTimeForm freeTimeForm) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.postForEntity("http://appointment-service/appointment/listFreeTime", freeTimeForm, ResponseObject.class);
            return response.getBody().getData();
        } catch (Exception e) {
            return null;
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
