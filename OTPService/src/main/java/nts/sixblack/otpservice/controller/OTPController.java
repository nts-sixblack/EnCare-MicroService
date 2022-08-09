package nts.sixblack.otpservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OTPController {

    @Autowired
    SmsService smsService;

    @Autowired
    AccountService accountService;

    @PostMapping("/send")
    public ResponseEntity<ResponseObject> sendOtp(@Valid @RequestBody PhoneForm phoneForm) {
        if (Format.phoneNotCorrectFormat(phoneForm.getPhone())) {
            return Response.phoneNotCorrectFormat();
        }
        if (smsService.sendMessage(phoneForm.getPhone())) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Send OTP success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Send OTP fail", "Incorrect phone number")
        );
    }

    @PostMapping("/confirm")
    public ResponseEntity<ResponseObject> confirmOtp(@Valid @RequestBody OTPForm otpForm) {
        if (Format.phoneNotCorrectFormat(otpForm.getPhone())) {
            return Response.phoneNotCorrectFormat();
        }
        if (Format.otpNotCorrectFormat(otpForm.getOtp())) {
            return Response.otpNotCorrectFormat();
        }
        otpForm.setPhone("+84" + Long.parseLong(otpForm.getPhone()));
        if (accountService.confirmOTP(otpForm)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Confirm OTP success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Confirm OTP fail", "OTP is wrong or phone is wrong")
        );
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ResponseObject> newPassword(@Valid @RequestBody NewPasswordFormForget newPasswordFormForget) {
        if (Format.phoneNotCorrectFormat(newPasswordFormForget.getPhone())) {
            return Response.phoneNotCorrectFormat();
        }
        if (Format.otpNotCorrectFormat(newPasswordFormForget.getOtp())) {
            return Response.otpNotCorrectFormat();
        }
        newPasswordFormForget.setPhone("+84" + Long.parseLong(newPasswordFormForget.getPhone()));
        if (accountService.confirmOTP(new OTPForm(newPasswordFormForget.getOtp(), newPasswordFormForget.getPhone()))) {
            accountService.newPassowrd(newPasswordFormForget);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Change password success", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Confirm OTP fail", "OTP is wrong or phone number is wrong")
            );
        }
    }
}
