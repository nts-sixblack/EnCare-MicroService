package nts.sixblack.appointmentservice.controller;

import nts.sixblack.appointmentservice.form.FeedbackForm;
import nts.sixblack.appointmentservice.response.ResponseObject;
import nts.sixblack.appointmentservice.service.AppointmentService;
import nts.sixblack.appointmentservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/exist/{accountId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long accountId) {
        if (feedbackService.existFeedback(accountId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{feedbackId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long feedbackId) {
        if (feedbackService.existFeedback(feedbackId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Feedback", feedbackService.findById(feedbackId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseObject> feedback(@Valid @RequestBody FeedbackForm feedbackForm){
        long accountId = 1;
        feedbackForm.setAccountUserId(accountId);

        if (appointmentService.existAppointment(feedbackForm.getAppointmentId())) {
            if(feedbackService.newFeedback(feedbackForm)){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200,"Feedback success","")
                );
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Feedback fail","This appointment already feedback")
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );

    }
}
