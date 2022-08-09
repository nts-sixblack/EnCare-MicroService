package nts.sixblack.zuulservice.controller;

import nts.sixblack.zuulservice.form.InformationForm;
import nts.sixblack.zuulservice.form.NewPasswordForm;
import nts.sixblack.zuulservice.jwt.JwtTokenProvider;
import nts.sixblack.zuulservice.response.AccountResponse;
import nts.sixblack.zuulservice.response.Response;
import nts.sixblack.zuulservice.response.ResponseObject;
import nts.sixblack.zuulservice.service.AccountService;
import nts.sixblack.zuulservice.service.DoctorService;
import nts.sixblack.zuulservice.util.CustomUserDetail;
import nts.sixblack.zuulservice.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/exist/{accountId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long accountId) {
        if (accountService.existAccount(accountId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{accountId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long accountId) {
        if (accountService.existAccount(accountId)) {
            AccountResponse accountResponse = accountService.findById(accountId);
            System.out.println(accountResponse.toString());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information account", accountResponse)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseObject> newPassword(@Valid @RequestBody NewPasswordForm newPasswordForm){

        if(accountService.updatePassword(newPasswordForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Update new password success","")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Update new password fail","Old password fail")
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody InformationForm informationForm){
        if (!informationForm.getBirthDay().isBlank()){
            if (Format.dayNotCorrectFormat(informationForm.getBirthDay())){
                return Response.dayNotCorrectFormat();
            }
        }

        if (accountService.updateInformation(informationForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Update Information success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "The date of birth exceeds the current time", "")
        );
    }

    @GetMapping("/findDoctor")
    public ResponseEntity<ResponseObject> findDoctorByName(@RequestParam(name = "name", required = true) String name,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor by name", doctorService.findLikeName(name, page))
        );
    }

    private long getAccountId(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
                return userDetail.getAccount().getAccountId();
            }
            return 0;
        } catch (Exception e) {
//            e.printStackTrace();
            return 0;
        }
    }

}
