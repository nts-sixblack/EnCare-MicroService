package nts.sixblack.appointmentservice.repository.dao;

import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Status;
import nts.sixblack.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AppointmentDao {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public boolean existAppointment(long appointmentId) {
        return appointmentRepository.existsById(appointmentId);
    }

    public Appointment findById(long appointmentId) {
        try {
            Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
            if (appointment == null) {
                return null;
            }
            return appointment;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Appointment> findByDoctorIdAndTimeAndDayEquals(long doctorId, int time, Date date) {
        return appointmentRepository.findByDoctorIdAndTimeAndDayEquals(doctorId, time, date);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findByUserIdAndStatus(long userId, long statusId, Pageable pageable) {
        Status status = new Status(statusId);
        return appointmentRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public Appointment findByAppointmentIdAndUserId(long appointmentId, long userId) {
        return appointmentRepository.findByAppointmentIdAndUserId(appointmentId, userId);
    }

    public List<Appointment> findByDoctorIdAndDay(long doctorId, Date date) {
        return appointmentRepository.findByDoctorIdAndDayEquals(doctorId, date);
    }
}
