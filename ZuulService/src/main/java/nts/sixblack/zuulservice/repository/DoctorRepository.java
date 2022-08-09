package nts.sixblack.zuulservice.repository;

import nts.sixblack.zuulservice.model.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("select d from Doctor d where d.accountId in (select a from Account a where a.name like %?1% ) order by d.rating")
    List<Doctor> findDoctorByName(String name, Pageable pageable);
}