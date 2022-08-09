package nts.sixblack.zuulservice.service;

import nts.sixblack.zuulservice.config.NumberConfig;
import nts.sixblack.zuulservice.form.RegisterFormDoctor;
import nts.sixblack.zuulservice.model.Account;
import nts.sixblack.zuulservice.model.Category;
import nts.sixblack.zuulservice.model.Doctor;
import nts.sixblack.zuulservice.model.Hospital;
import nts.sixblack.zuulservice.repository.dao.DoctorDao;
import nts.sixblack.zuulservice.response.AccountResponse;
import nts.sixblack.zuulservice.response.CategoryResponse;
import nts.sixblack.zuulservice.response.DoctorResponse;
import nts.sixblack.zuulservice.response.HospitalResponse;
import nts.sixblack.zuulservice.util.RestfulData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RestfulData restfulData;

    public boolean register(RegisterFormDoctor registerFormDoctor) {
        long id = accountService.registerDoctor(registerFormDoctor);
        if (id != 0) {
            Account account = new Account(id);
            Category category = new Category(registerFormDoctor.getCategoryId());
            Hospital hospital = new Hospital(registerFormDoctor.getHospitalId());

            Doctor doctor = new Doctor();
            doctor.setRating(0f);
            doctor.setCountRating(0);
            doctor.setAccountId(account.getAccountId());
            doctor.setCategory(category);
            doctor.setHospital(hospital);

            doctorDao.save(doctor);
            return true;
        }
        return false;
    }

    public List<DoctorResponse> findLikeName(String name, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorDao.findDoctorByName(name, pageable);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        for (Doctor doctor:doctorList){
            DoctorResponse doctorResponse = transformData(doctor);

            doctorResponse.setAccountResponse(accountService.findById(doctorResponse.getAccountResponse().getAccountId()));
            doctorResponse.setCategoryResponse(restfulData.informationCategory(doctorResponse.getCategoryResponse().getCategoryId()));

            doctorResponseList.add(doctorResponse);
        }
        return doctorResponseList;
    }

    private DoctorResponse transformData(Doctor doctor) {
        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setDoctorId(doctor.getDoctorId());
        doctorResponse.setRating(NumberConfig.round(doctor.getRating()));
        doctorResponse.setCountRating(doctor.getCountRating());

//        ///////
        doctorResponse.setAccountResponse(new AccountResponse(doctor.getAccountId()));
        doctorResponse.setCategoryResponse(new CategoryResponse(doctor.getCategory().getCategoryId()));
        doctorResponse.setHospitalResponse(new HospitalResponse(doctor.getHospital().getHospitalId()));
//          ////////////
        return doctorResponse;
    }
}
