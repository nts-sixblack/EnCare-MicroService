package nts.sixblack.zuulservice.controller;

import nts.sixblack.zuulservice.config.RegexConfig;
import nts.sixblack.zuulservice.form.LoginForm;
import nts.sixblack.zuulservice.form.RegisterFormDoctor;
import nts.sixblack.zuulservice.form.RegisterFormUser;
import nts.sixblack.zuulservice.jwt.JwtTokenProvider;
import nts.sixblack.zuulservice.model.Account;
import nts.sixblack.zuulservice.response.LoginResponse;
import nts.sixblack.zuulservice.response.Response;
import nts.sixblack.zuulservice.response.ResponseObject;
import nts.sixblack.zuulservice.service.CategoryService;
import nts.sixblack.zuulservice.service.DoctorService;
import nts.sixblack.zuulservice.service.HospitalService;
import nts.sixblack.zuulservice.service.UserService;
import nts.sixblack.zuulservice.util.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HomeController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody LoginForm loginForm){

        if (phoneNotFormat(loginForm.getPhone())) {
            return Response.phoneNotCorrectFormat();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "+84"+Long.parseLong(loginForm.getPhone().trim()),
                        loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Account account = customUserDetail.getAccount();
        String token = jwtTokenProvider.generateToken(customUserDetail);
        LoginResponse loginResponse = new LoginResponse(account.getAccountId(), account.getRole(), account.getPassword(), token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"login success", loginResponse)
        );
    }

    private boolean phoneNotFormat(String phone){
        return !phone.matches(RegexConfig.phone);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody RegisterFormUser registerFormUser){
        if (phoneNotFormat(registerFormUser.getPhone())) {
            return Response.phoneNotCorrectFormat();
        }
        if (nameNotFormat(registerFormUser.getName())) {
            return Response.nameNotCorrectFormat();
        }
        if (userService.register(registerFormUser)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Register Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "This phone number already exists")
        );
    }

    private boolean nameNotFormat(String name){
        return !name.matches(RegexConfig.name);
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<ResponseObject> registerDoctor(@Valid @RequestBody RegisterFormDoctor registerFormDoctor){
        if (phoneNotFormat(registerFormDoctor.getPhone())){
            return Response.phoneNotCorrectFormat();
        }
        if (nameNotFormat(registerFormDoctor.getName())){
            return Response.nameNotCorrectFormat();
        }
        if (dayNotFormat(registerFormDoctor.getBirthDay())){
            return Response.dayNotCorrectFormat();
        }
        if (hospitalService.existHospital(registerFormDoctor.getHospitalId())) {
            return Response.notExistHospital();
        }
        if (categoryService.existCategory(registerFormDoctor.getHospitalId())) {
            return Response.notExistCategory();
        }
        if (doctorService.register(registerFormDoctor)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Register Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "This phone number already exists")
        );
    }

    private boolean dayNotFormat(String day){
        return !day.matches(RegexConfig.day);
    }


}
