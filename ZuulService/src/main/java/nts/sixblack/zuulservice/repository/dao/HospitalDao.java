package nts.sixblack.zuulservice.repository.dao;

import nts.sixblack.zuulservice.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDao {

    @Autowired
    HospitalRepository hospitalRepository;

    public boolean existHospital(long hospitalId) {
        return !hospitalRepository.existsById(hospitalId);
    }
}
