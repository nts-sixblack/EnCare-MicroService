package nts.sixblack.doctorservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<ResponseObject> phoneNotCorrectFormat() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Phone number is not in the correct format")
        );
    }

    public static ResponseEntity<ResponseObject> nameNotCorrectFormat() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Name is not in the correct format")
        );
    }

    public static ResponseEntity<ResponseObject> dayNotCorrectFormat() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Day is not in the correct format")
        );
    }

    public static ResponseEntity<ResponseObject> notExistHospital() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Not exist hospital")
        );
    }

    public static ResponseEntity<ResponseObject> notExistCategory() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Not exist category")
        );
    }

    public static ResponseEntity<ResponseObject> notExistPatient() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Not exist patient")
        );
    }

    public static ResponseEntity<ResponseObject> notExistDoctor() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Fail", "Not exist doctor")
        );
    }

}
