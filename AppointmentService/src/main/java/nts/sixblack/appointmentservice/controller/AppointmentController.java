package nts.sixblack.appointmentservice.controller;

import nts.sixblack.appointmentservice.form.AppointmentForm;
import nts.sixblack.appointmentservice.form.FreeTimeForm;
import nts.sixblack.appointmentservice.response.Response;
import nts.sixblack.appointmentservice.response.ResponseObject;
import nts.sixblack.appointmentservice.service.AppointmentService;
import nts.sixblack.appointmentservice.util.Format;
import nts.sixblack.appointmentservice.util.RestfulData;
import nts.sixblack.appointmentservice.util.ValueStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private RestfulData restfulData;

    @GetMapping("/exist/{appointmentId}")
    public ResponseEntity<ResponseObject> existAppointment(@PathVariable long appointmentId) {
        if (appointmentService.existAppointment(appointmentId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this appointment", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", false)
        );
    }

    @GetMapping("/information/{appointmentId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long appointmentId) {
        if (appointmentService.existAppointment(appointmentId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Appointment", appointmentService.findById(appointmentId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseObject> newAppointment(@Valid @RequestBody AppointmentForm appointmentForm) {
        long accountId = 1;
        appointmentForm.setAccountUserId(accountId);

        if (Format.dayNotCorrectFormat(appointmentForm.getDay())) {
            return Response.dayNotCorrectFormat();
        }

        if (restfulData.existDoctor(appointmentForm.getDoctorId())) {
            if (appointmentService.newAppointment(appointmentForm)) {
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

    @GetMapping("/history")
    public ResponseEntity<ResponseObject> historyAppointment(@RequestParam(required = true, name = "userId") long userId,
                                                             @RequestParam(required = false, name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "status") long valueStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "History appointment", appointmentService.historyAppointmentUser(userId, valueStatus, page))
        );
    }

    @GetMapping("/confirm")
    public ResponseEntity<ResponseObject> confirmAppointment(@RequestParam(required = true, name = "appointmentId") long appoinmentId) {

        long accountId = 1;

        if (appointmentService.existAppointment(appoinmentId)) {
            appointmentService.updateStatusAppointment(accountId, appoinmentId, ValueStatus.CONFIRMED);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel Appointment success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );

    }

    @GetMapping("/success")
    public ResponseEntity<ResponseObject> successAppointment(@RequestParam(required = true, name = "appointmentId") long appoinmentId) {

        long accountId = 1;

        if (appointmentService.existAppointment(appoinmentId)) {
            appointmentService.updateStatusAppointment(accountId, appoinmentId, ValueStatus.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel Appointment success", "")
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );

    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseObject> cancelAppointment(@RequestParam(required = true, name = "userId") long userId,
                                                            @RequestParam(required = true, name = "appointmentId") long appointmentId) {

        if (appointmentService.existAppointment(appointmentId)) {
            appointmentService.updateStatusAppointment(userId, appointmentId, ValueStatus.CANCEL);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel Appointment success", "")
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );

    }

    @PostMapping("/listFreeTime")
    public ResponseEntity<ResponseObject> listFreeTimeOfDoctor(@Valid @RequestBody FreeTimeForm freeTimeForm){
        if (Format.dayNotCorrectFormat(freeTimeForm.getTime())){
            return Response.dayNotCorrectFormat();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List free time", appointmentService.listFreeTime(freeTimeForm))
        );
    }

}
