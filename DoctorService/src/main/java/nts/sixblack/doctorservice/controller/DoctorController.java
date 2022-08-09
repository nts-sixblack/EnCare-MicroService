package nts.sixblack.doctorservice.controller;

import nts.sixblack.doctorservice.form.UpdateRatingForm;
import nts.sixblack.doctorservice.response.ResponseObject;
import nts.sixblack.doctorservice.service.CategoryService;
import nts.sixblack.doctorservice.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/exist/{accountId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long accountId) {
        if (doctorService.existAccount(accountId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{doctorId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long doctorId) {
        if (doctorService.existAccount(doctorId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Feedback", doctorService.findById(doctorId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @PostMapping("/updateRating")
    public void updateRating(@RequestBody UpdateRatingForm updateRatingForm) {
        System.out.println(updateRatingForm.toString());

        doctorService.updateRating(updateRatingForm.getDoctorId(), updateRatingForm.getRating());
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<ResponseObject> listDoctorOfCategoryIdAndRating(
            @RequestParam(required = true, name = "categoryId") long categoryId,
            @RequestParam(required = false, name = "lon", defaultValue = "0") double lon,
            @RequestParam(required = false, name = "lat", defaultValue = "0") double lat,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "rating", defaultValue = "0") int rating){

        if (categoryService.existCategory(categoryId)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "List Category", doctorService.listDoctorOfCategoryRating(categoryId, page, rating, lon, lat))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This category does not exist", "")
        );
    }

}
