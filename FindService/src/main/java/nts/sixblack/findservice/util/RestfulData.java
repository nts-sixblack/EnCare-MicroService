package nts.sixblack.findservice.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nts.sixblack.findservice.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public Object findDoctorByName(String name, int page) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://gateway/account/findDoctor?name="+name+"&page="+page, ResponseObject.class);
            return response.getBody().getData();
        } catch (Exception e) {
            return null;
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

    public Object listDoctorByName(String name, int page) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://doctor-service/doctor/find?name="+name+"&page="+page, ResponseObject.class);
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

    public boolean existFeedback(long doctorId) {
        try {
            ResponseEntity<ResponseObject> response = restTemplate.getForEntity("http://appointment-service/feedback/exist/"+doctorId, ResponseObject.class);
            return existData(response);
        } catch (Exception e) {
            return false;
        }
    }

}
