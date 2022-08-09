package nts.sixblack.doctorservice.service;

import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.model.Hospital;
import nts.sixblack.doctorservice.repository.dao.HospitalDao;
import nts.sixblack.doctorservice.response.DoctorResponse;
import nts.sixblack.doctorservice.response.HospitalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    @Autowired
    private HospitalDao hospitalDao;

    public boolean existAccount(long accountId) {
        return hospitalDao.existAccount(accountId);
    }

    public HospitalResponse findById(long id) {
        Hospital hospital = hospitalDao.findByDoctorId(id);
        if (hospital != null) {
            return transformData(hospital);
        }
        return null;
    }

    private HospitalResponse transformData(Hospital hospital){
        HospitalResponse hospitalResponse = new HospitalResponse();

        hospitalResponse.setHospitalId(hospital.getHospitalId());
        hospitalResponse.setDescription(hospital.getDescription());
        hospitalResponse.setLatMap(hospital.getLatMap());
        hospitalResponse.setLongMap(hospital.getLongMap());
        hospitalResponse.setRating(hospital.getRating());
        hospitalResponse.setCountRating(hospital.getCountRating());
        hospitalResponse.setAddress(hospital.getAddress());
        hospitalResponse.setName(hospital.getName());

        return hospitalResponse;
    }
}
