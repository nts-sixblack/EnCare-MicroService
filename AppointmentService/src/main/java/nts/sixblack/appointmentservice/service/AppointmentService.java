package nts.sixblack.appointmentservice.service;

import nts.sixblack.appointmentservice.form.AppointmentForm;
import nts.sixblack.appointmentservice.form.FreeTimeForm;
import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Status;
import nts.sixblack.appointmentservice.repository.dao.AppointmentDao;
import nts.sixblack.appointmentservice.response.AppointmentResponse;
import nts.sixblack.appointmentservice.util.RestfulData;
import nts.sixblack.appointmentservice.config.TimeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private RestfulData restfulData;

    //    @Autowired
//    private DoctorService doctorService;
//
//    @Autowired
//    private UserService userService;
//
    @Autowired
    private StatusService statusService;

    public boolean existAppointment(long appointmentId) {
        return appointmentDao.existAppointment(appointmentId);
    }

    public AppointmentResponse findById(long id) {
        Appointment appointment = appointmentDao.findById(id);
        if (appointment == null) {
            return null;
        }
        return transformData(appointment);
    }

    private AppointmentResponse transformData(Appointment appointment) {
        AppointmentResponse appointmentResponse = new AppointmentResponse();

        appointmentResponse.setAppointmentId(appointment.getAppointmentId());
        appointmentResponse.setSymptoms(appointment.getSymptoms());
        appointmentResponse.setDescription(appointment.getDescription());
        appointmentResponse.setTime(appointment.getTime());
        appointmentResponse.setDay(TimeConfig.getTime(appointment.getDay()));
        appointmentResponse.setCreateDate(TimeConfig.getTime(appointment.getCreateDate()));
        appointmentResponse.setDoctorId(appointment.getDoctorId());
        appointmentResponse.setUserId(appointment.getUserId());
        /////////////////
//        appointmentResponse.setDoctorResponse(doctorService.findById(appointment.getDoctor().getDoctorId()));
//        appointmentResponse.setUserResponse(userService.findById(appointment.getUser().getUserId()));
        appointmentResponse.setStatus(statusService.getStatus(appointment.getStatus().getStatusId()));
        //////////////////

        return appointmentResponse;
    }

    public boolean newAppointment(AppointmentForm appointmentForm) {

        Date day = TimeConfig.getDate(appointmentForm.getDay());
        int time = appointmentForm.getTime();
        //////////////
//        long userId = restfulData.informationPatientByAccountUserId(appointmentForm.getAccountUserId()).getUserId();
        //////////////
        if (findTimeAndDay(appointmentForm.getDoctorId(), time, day)) {
            Status status = new Status(1); // Đang chờ xác nhận

            Appointment appointment = new Appointment();
            appointment.setUserId(appointmentForm.getUserId());
            appointment.setDoctorId(appointmentForm.getDoctorId());
            appointment.setTime(time);
            appointment.setDay(day);
//            appointment.setDescription(appointmentForm.getDescription().trim());
            appointment.setStatus(status);
            appointment.setSymptoms(appointmentForm.getSymptomps().trim());
            appointment.setCreateDate(new Date());

            appointmentDao.save(appointment);
            return true;
        }
        return false;
    }

    public List<AppointmentResponse> historyAppointmentUser(long userId, long status, int page) {
        ////////////
//        long userId = userService.findUserIdByAccountId(accountId);
        ////////////
//        User user = new User(userId);
        Pageable pageable = PageRequest.of(page, 6);
        List<Appointment> appointmentList = appointmentDao.findByUserIdAndStatus(userId, status, pageable);
        List<AppointmentResponse> appointmentResponseList = new ArrayList<AppointmentResponse>();
        for (Appointment appointment:appointmentList){
            AppointmentResponse appointmentResponse = transformData(appointment);
            appointmentResponseList.add(appointmentResponse);
        }
        return appointmentResponseList;
    }

    public boolean updateStatusAppointment(long userId, long appointmentId, long value) {
        ///////////
//        long userId = userService.findUserIdByAccountId(accountId);
        //////////
//        User user = new User(userId);
        Appointment appointment = appointmentDao.findByAppointmentIdAndUserId(appointmentId, userId);
        if (appointment == null){
            return false;
        }
        Status status = new Status(value);
        appointment.setStatus(status);
        appointmentDao.save(appointment);
        return true;
    }

    public boolean findTimeAndDay(long doctorId, int time, Date date){
        List<Appointment> appointment = appointmentDao.findByDoctorIdAndTimeAndDayEquals(doctorId, time, date);
        if (appointment.size() > 0){
            return false;
        }
        return true;
    }

    public List<Integer> listFreeTime(FreeTimeForm freeTimeForm) {
        Date date = TimeConfig.getDate(freeTimeForm.getTime());
        boolean check = true;
        List<Appointment> appointmentList = appointmentDao.findByDoctorIdAndDay(freeTimeForm.getDoctorId(), date);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 7; i <= 11; i++) {
            check = true;
            for (int j = 0; j < appointmentList.size(); j++) {
                if (i == appointmentList.get(j).getTime()) {
                    check = false;
                    break;
                }
            }
            if (check == true) {
                list.add(i);
            }
        }
        for (int i = 13; i <= 16; i++) {
            check = true;
            for (int j = 0; j < appointmentList.size(); j++) {
                if (i == appointmentList.get(j).getTime()) {
                    check = false;
                    break;
                }
            }
            if (check == true) {
                list.add(i);
            }
        }
        return list;
    }

}
