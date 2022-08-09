package nts.sixblack.doctorservice.repository;

import nts.sixblack.doctorservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(long categoryId);
}