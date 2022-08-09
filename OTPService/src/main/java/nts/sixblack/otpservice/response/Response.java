package nts.sixblack.otpservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<ResponseObject> phoneNotCorrectFormat() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Fail", "Phone number is not in the correct format")
        );
    }

    public static ResponseEntity<ResponseObject> otpNotCorrectFormat() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Fail", "OTP is not in the correct format")
        );
    }
}
