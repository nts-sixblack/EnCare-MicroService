package nts.sixblack.doctorservice.service;

import nts.sixblack.doctorservice.config.NumberConfig;
import nts.sixblack.doctorservice.form.mapbox.Location;
import nts.sixblack.doctorservice.model.Doctor;
import nts.sixblack.doctorservice.repository.dao.DoctorDao;
import nts.sixblack.doctorservice.response.AccountResponse;
import nts.sixblack.doctorservice.response.CategoryResponse;
import nts.sixblack.doctorservice.response.DoctorResponse;
import nts.sixblack.doctorservice.response.HospitalResponse;
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
    private MapboxService mapboxService;

    @Autowired
    private HospitalService hospitalService;

    public boolean existAccount(long accountId) {
        return doctorDao.existAccount(accountId);
    }

    public DoctorResponse findById(long id) {
        Doctor user = doctorDao.findByDoctorId(id);
        if (user != null) {
            return transformData(user);
        }
        return null;
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

    public void updateRating(long doctorId, float number) {
        Doctor doctor = doctorDao.findByDoctorId(doctorId);
        long count = doctor.getCountRating();
        float rating = doctor.getRating();
        float value = (count*rating+number)/(count+1);
        doctor.setRating(value);
        doctor.setCountRating(count+1);

        doctorDao.save(doctor);
    }

    public List<DoctorResponse> listDoctorOfCategoryRating(long categoryId, int page, float rating, double lon, double lat) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorDao.findDoctorByCategoryAndRatingDesc(categoryId, rating, pageable);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();

        if (lon != 0 && lat != 0) {
            Location start = new Location(lon, lat);
            for (Doctor doctor : doctorList) {
                DoctorResponse doctorResponse = transformData(doctor);
                HospitalResponse hospitalResponse = hospitalService.findById(doctor.getHospital().getHospitalId());
                Location end = new Location(hospitalResponse.getLongMap(), hospitalResponse.getLatMap());
                doctorResponse.setDistance(mapboxService.getDistance(start, end));
                doctorResponseList.add(doctorResponse);
            }
        } else {
            for (Doctor doctor : doctorList) {
                DoctorResponse doctorResponse = transformData(doctor);
                doctorResponseList.add(doctorResponse);
            }
        }
        return doctorResponseList;
    }

}
