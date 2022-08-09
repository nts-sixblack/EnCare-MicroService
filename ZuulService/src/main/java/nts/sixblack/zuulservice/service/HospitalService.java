package nts.sixblack.zuulservice.service;

import nts.sixblack.zuulservice.repository.dao.HospitalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    @Autowired
    HospitalDao hospitalDao;

    public boolean existHospital(long hospitalId) {
        return hospitalDao.existHospital(hospitalId);
    }
}
