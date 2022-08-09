package nts.sixblack.patientservice.controller;

import nts.sixblack.patientservice.config.RegexConfig;
import nts.sixblack.patientservice.form.AppointmentForm;
import nts.sixblack.patientservice.form.FeedbackForm;
import nts.sixblack.patientservice.form.NewPasswordForm;
import nts.sixblack.patientservice.jwt.JwtTokenProvider;
import nts.sixblack.patientservice.response.Response;
import nts.sixblack.patientservice.response.ResponseObject;
import nts.sixblack.patientservice.response.UserResponse;
import nts.sixblack.patientservice.service.UserService;
import nts.sixblack.patientservice.util.Format;
import nts.sixblack.patientservice.util.RestfulData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private RestfulData restfulData;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/exist/{userId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long userId) {
        if (userService.existAccount(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{userId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long userId) {
        if (userService.existAccount(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Feedback", userService.findById(userId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @GetMapping("/accountId/{accountUserId}")
    public ResponseEntity<ResponseObject> findByAccountUserId(@PathVariable long accountUserId) {
        UserResponse userResponse = userService.findByAccountUserId(accountUserId);
        if (userResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This account does not exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Information Feedback", userResponse)
        );
    }

    @PostMapping("/newAppointment")
    public ResponseEntity<ResponseObject> newAppointment(@Valid @RequestBody AppointmentForm appointmentForm,
                                                         @RequestHeader(value = "Auth") String token) {
        long accountId = getAccountId(token);
        if (accountId == 0) return null;
        appointmentForm.setAccountUserId(accountId);

        appointmentForm.setUserId(userService.findByAccountUserId(appointmentForm.getAccountUserId()).getUserId());

        System.out.println(appointmentForm.getUserId());

        if (!appointmentForm.getDay().matches(RegexConfig.day)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Day is not correct format", "")
            );
        }

        if (restfulData.existDoctor(appointmentForm.getDoctorId())) {
            if (restfulData.newAppointment(appointmentForm)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "New appointment success", "")
                );
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "The time you selected has been scheduled", "")
            );

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This doctor does not exist", "")
        );

    }

    private long getAccountId(String token) {
        return jwtTokenProvider.getUserId(token.substring(7));
    }

    @PostMapping("/newFeedback")
    public ResponseEntity<ResponseObject> feedback(@Valid @RequestBody FeedbackForm feedbackForm,
                                                   @RequestHeader(value = "Auth") String token) {

        long accountId = getAccountId(token);

        feedbackForm.setAccountUserId(accountId);
        feedbackForm.setUserId(userService.findByAccountUserId(feedbackForm.getAccountUserId()).getUserId());

        if (restfulData.existAppointment(feedbackForm.getAppointmentId())) {
            if (restfulData.newFeedback(feedbackForm)) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Feedback success", "")
                );
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Feedback fail", "This appointment already feedback")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseObject> historyAppointment(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "status") int value,
                                                             @RequestHeader(value = "Auth") String token) {
        if ((value < 1) || (value > 4)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Fail", "Không tồn tại đơn hàng có trạng thái này")
            );
        }
        long accountId = getAccountId(token);

        long userId = userService.findByAccountUserId(accountId).getUserId();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "History appointment", restfulData.historyAppointment(userId, page, value))
        );
    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseObject> cancelAppointment(@RequestParam(required = true, name = "appointmentId") long appointmentId,
                                                            @RequestHeader(value = "Auth") String token) {

        long accountId = getAccountId(token);
        long userId = userService.findByAccountUserId(accountId).getUserId();

        if (restfulData.existAppointment(appointmentId)) {
            restfulData.cancelAppointment(userId, appointmentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel Appointment success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseObject> newPassword(@Valid @RequestBody NewPasswordForm newPasswordForm,
                                                      @RequestHeader(value = "Auth") String token) {

        long accountId = getAccountId(token);

        newPasswordForm.setAccountId(accountId);

        if (restfulData.changePassword(newPasswordForm)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Update new password success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Update new password fail", "Old password fail")
        );
    }

    @GetMapping("/myProfile")
    public ResponseEntity<ResponseObject> myInformation(@RequestHeader(value = "Auth") String token) {

        long accountId = getAccountId(token);
        UserResponse userResponse = new UserResponse(
                userService.findByAccountUserId(accountId).getUserId(),
                restfulData.informationAccount(accountId)
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "My Information", userResponse)
        );
    }
//    @GetMapping("/listAppointment")
//    public ResponseEntity<ResponseObject> listAppointment(@RequestParam(name = "status") int status,
//                                                          @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
//        if ((status < 1) || (status > 4)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject(400, "Fail", "This value does not exist")
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Appointment of user", appointmentService.listAppointment(
//                        getAccountId(), status, page
//                ))
//        );
//    }
}
