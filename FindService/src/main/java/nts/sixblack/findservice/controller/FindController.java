package nts.sixblack.findservice.controller;

import nts.sixblack.findservice.response.ResponseObject;
import nts.sixblack.findservice.util.RestfulData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindController {

    @Autowired
    RestfulData restfulData;

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ResponseObject> findDoctorById(@PathVariable long doctorId) {
        if (restfulData.existDoctor(doctorId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this doctor", restfulData.informationDoctor(doctorId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This doctor does not exist", "")
        );
    }

    @GetMapping("/doctor")
    public ResponseEntity<ResponseObject> findDoctorLikeName(
            @RequestParam(required = true, name = "name") String name,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor by name", restfulData.findDoctorByName(name, page))
        );
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<ResponseObject> findHospitalById(@PathVariable long hospitalId) {
        if (restfulData.existHospital(hospitalId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this hospital", restfulData.informationHospital(hospitalId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This hospital does not exist", "")
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseObject> findPatientById(@PathVariable long patientId) {
        if (restfulData.existPatient(patientId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this patient", restfulData.informationPatient(patientId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This patient does not exist", "")
        );
    }



}
