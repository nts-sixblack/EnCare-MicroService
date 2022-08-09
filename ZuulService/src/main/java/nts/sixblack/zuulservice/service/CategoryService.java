package nts.sixblack.zuulservice.service;

import nts.sixblack.zuulservice.repository.dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public boolean existCategory(long categoryId) {
        return categoryDao.existCategory(categoryId);
    }
}
