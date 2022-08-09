package nts.sixblack.zuulservice.repository.dao;

import nts.sixblack.zuulservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDao {

    @Autowired
    CategoryRepository categoryRepository;

    public boolean existCategory(long categoryId) {
        return !categoryRepository.existsById(categoryId);
    }
}
