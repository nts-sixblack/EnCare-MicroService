package nts.sixblack.doctorservice.repository;

import nts.sixblack.doctorservice.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Hospital findByHospitalId(long hospitalId);
}