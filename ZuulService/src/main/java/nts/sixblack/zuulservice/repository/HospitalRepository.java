package nts.sixblack.zuulservice.repository;

import nts.sixblack.zuulservice.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}