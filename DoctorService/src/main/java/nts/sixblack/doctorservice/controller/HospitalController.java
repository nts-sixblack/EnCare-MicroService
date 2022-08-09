package nts.sixblack.doctorservice.controller;

import nts.sixblack.doctorservice.response.ResponseObject;
import nts.sixblack.doctorservice.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/exist/{accountId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long accountId) {
        if (hospitalService.existAccount(accountId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{hospitalId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long hospitalId) {
        if (hospitalService.existAccount(hospitalId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Feedback", hospitalService.findById(hospitalId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }
}
