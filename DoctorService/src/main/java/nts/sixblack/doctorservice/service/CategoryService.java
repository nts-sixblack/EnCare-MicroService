package nts.sixblack.doctorservice.service;

import nts.sixblack.doctorservice.model.Category;
import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.repository.dao.CategoryDao;
import nts.sixblack.doctorservice.response.CategoryResponse;
import nts.sixblack.doctorservice.response.DoctorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public boolean existCategory(long accountId) {
        return categoryDao.existAccount(accountId);
    }

    public CategoryResponse findById(long id) {
        Category user = categoryDao.findByDoctorId(id);
        if (user != null) {
            return transformData(user);
        }
        return null;
    }

    private CategoryResponse transformData(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());

        return categoryResponse;
    }

    public List<CategoryResponse> listCategory() {
        List<Category> categoryList = categoryDao.listCategory();
        List<CategoryResponse> categoryResponseList = new ArrayList<CategoryResponse>();
        for (Category category:categoryList) {
            CategoryResponse categoryResponse = transformData(category);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }
}
