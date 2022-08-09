package nts.sixblack.zuulservice.repository.dao;

import nts.sixblack.zuulservice.model.Doctor;
import nts.sixblack.zuulservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorDao {

    @Autowired
    DoctorRepository doctorRepository;

    public Doctor save(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findDoctorByName(String name, Pageable pageable) {
        return doctorRepository.findDoctorByName(name, pageable);
    }
}
