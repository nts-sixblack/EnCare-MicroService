package nts.sixblack.doctorservice.repository.dao;

import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorDao {

    @Autowired
    private DoctorRepository doctorRepository;

    public boolean existAccount(long accountId) {
        return doctorRepository.existsById(accountId);
    }

    public Doctor findByDoctorId(long userId) {
        try {
            Doctor doctor = doctorRepository.findByDoctorId(userId);
            if (doctor == null) {
                return null;
            }
            return doctor;
        } catch (Exception e) {
            return null;
        }
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findDoctorByCategoryAndRatingDesc(long categoryId, float rating, Pageable pageable){
        return doctorRepository.findDoctorByCategoryAndRatingDesc(categoryId, rating, pageable);
    }

}
