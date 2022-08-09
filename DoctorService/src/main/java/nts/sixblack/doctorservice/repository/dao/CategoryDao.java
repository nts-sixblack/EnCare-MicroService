package nts.sixblack.doctorservice.repository.dao;

import nts.sixblack.doctorservice.model.Category;
import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDao {

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean existAccount(long accountId) {
        return categoryRepository.existsById(accountId);
    }

    public Category findByDoctorId(long userId) {
        try {
            Category category = categoryRepository.findByCategoryId(userId);
            if (category == null) {
                return null;
            }
            return category;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }
}
