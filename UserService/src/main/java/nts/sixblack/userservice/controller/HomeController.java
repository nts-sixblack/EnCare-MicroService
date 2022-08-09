package nts.sixblack.userservice.controller;

import nts.sixblack.userservice.form.FreeTimeForm;
import nts.sixblack.userservice.form.InformationForm;
import nts.sixblack.userservice.form.NewPasswordForm;
import nts.sixblack.userservice.jwt.JwtTokenProvider;
import nts.sixblack.userservice.response.Response;
import nts.sixblack.userservice.response.ResponseObject;
import nts.sixblack.userservice.util.Format;
import nts.sixblack.userservice.util.RestfulData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    @Autowired
    RestfulData restfulData;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody InformationForm informationForm,
                                                 @RequestHeader(value = "Auth") String token){
        if (!informationForm.getBirthDay().isBlank()){
            if (Format.dayNotCorrectFormat(informationForm.getBirthDay())){
                return Response.dayNotCorrectFormat();
            }
        }

//        informationForm.setAccountId(getAccountId());
        informationForm.setAccountId(getAccountId(token));
/////////////////////////////////////////////
        if (restfulData.updateInformation(informationForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Update Information success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "The date of birth exceeds the current time", "")
        );
    }

    private long getAccountId(String token) {
        return jwtTokenProvider.getUserId(token.substring(7));
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ResponseObject> newPassowrd(@Valid @RequestBody NewPasswordForm newPasswordForm,
                                                      @RequestHeader(value = "Auth") String token){
        newPasswordForm.setAccountId(getAccountId(token));

        if(restfulData.changePassword(newPasswordForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Update new password success","")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Update new password fail","Old password fail")
        );
    }

    @GetMapping("/listCategory")
    public ResponseEntity<ResponseObject> listCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "List Category", restfulData.listCategory())
        );
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<ResponseObject> listDoctorOfCategoryIdAndRating(
            @RequestParam(required = true, name = "categoryId") long categoryId,
            @RequestParam(required = false, name = "lon", defaultValue = "0") double lon,
            @RequestParam(required = false, name = "lat", defaultValue = "0") double lat,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "rating", defaultValue = "0") int rating){

        if (restfulData.existDoctor(categoryId)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "List Category", restfulData.listDoctorOfCategory(categoryId, page, rating, lon, lat))
            );
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject(200, "List Category", doctorService.listDoctorOfCategoryRating(categoryId, page, rating))
//            );

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This category does not exist", "")
        );
    }

    @PostMapping("/listFreeTime")
    public ResponseEntity<ResponseObject> listFreeTimeOfDoctor(@Valid @RequestBody FreeTimeForm freeTimeForm){
        if (Format.dayNotCorrectFormat(freeTimeForm.getTime())){
            return Response.dayNotCorrectFormat();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List free time", restfulData.listFreetime(freeTimeForm))
        );
    }

    @GetMapping("/appointment")
    public ResponseEntity<ResponseObject> informationAppointment(@RequestParam(required = true, name = "id", defaultValue = "0") long appointmentId){

        if (restfulData.existAppointment(appointmentId)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Appointment", restfulData.informationAppointment(appointmentId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Find fail", "Don't have appointment id")
        );
    }

//    @GetMapping("/check")
//    public ResponseEntity<ResponseObject> check(){
////        List<Category> categoryList = new ArrayList<Category>();
////        categoryList.add(new Category("Neuroscience", "Obtain effective methods of treatment and rehabilitation in the department of neurosurgery and general neurology."));
////        categoryList.add(new Category("Orthopaedic Surgery & Sports Medicine",
////                "Restore mobility thanks to our team of experienced chiropractors (bone and muscle)."));
////        categoryList.add(new Category("Oncology Department",
////                "Get diagnoses and treatments from our qualified specialists in various areas of oncology."));
////        categoryList.add(new Category("Pediatrics",
////                "Bring your child the best medical treatment with our highly qualified pediatricians."));
////        categoryList.add(new Category("Department of Otolaryngology",
////                "Consult with specialists with an excellent track record of treating ENT diseases."));
////        categoryList.add(new Category("Ophthalmology",
////                "Get the right eye treatment using accurate surgical devices and make an effective diagnosis."));
////        categoryService.saveAll(categoryList);
////
////        List<Status> statusList = new ArrayList<Status>();
////        statusList.add(new Status("Wait for confirmation", ""));
////        statusList.add(new Status("Confirmed", ""));
////        statusList.add(new Status("Checked", ""));
////        statusList.add(new Status("Cancelled", ""));
////        statusService.saveAll(statusList);
////
////        List<Hospital> hospitalList = new ArrayList<Hospital>();
////        hospitalList.add(new Hospital(
////                "Da Nang General Hospital",
////                0,
////                0,
////                0,
////                0,
////                "Da Nang",
////                "Da Nang General Hospital"
////        ));
////        hospitalService.saveAll(hospitalList);
////        categoryService.delete();
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "Find fail", "")
//        );
//    }

//    private long getAccountId(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication!=null){
//            String token = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
//            return jwtTokenProvider.getUserId(token);
//        }
//        return 0;
//    }
}
