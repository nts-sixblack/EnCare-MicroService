package nts.sixblack.doctorservice.repository.dao;

import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.model.Hospital;
import nts.sixblack.doctorservice.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalDao {

    @Autowired
    private HospitalRepository hospitalRepository;

    public boolean existAccount(long accountId) {
        return hospitalRepository.existsById(accountId);
    }

    public Hospital findByDoctorId(long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findByHospitalId(hospitalId);
            if (hospital == null) {
                return null;
            }
            return hospital;
        } catch (Exception e) {
            return null;
        }
    }
}
