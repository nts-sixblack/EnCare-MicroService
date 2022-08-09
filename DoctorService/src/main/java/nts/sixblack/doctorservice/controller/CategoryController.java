package nts.sixblack.doctorservice.controller;

import nts.sixblack.doctorservice.response.ResponseObject;
import nts.sixblack.doctorservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/exist/{accountId}")
    public ResponseEntity<ResponseObject> existAccount(@PathVariable long accountId) {
        if (categoryService.existCategory(accountId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Exist this account", true)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This account does not exist", false)
        );
    }

    @GetMapping("/information/{categoryId}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long categoryId) {
        if (categoryService.existCategory(categoryId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Feedback", categoryService.findById(categoryId))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This appointment does not exist", "")
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseObject> listCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List category", categoryService.listCategory())
        );
    }
}
