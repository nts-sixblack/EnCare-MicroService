package nts.sixblack.appointmentservice.repository;

import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentId(long appointmentId);
    List<Appointment> findByDoctorIdAndTimeAndDayEquals(long doctorId, int time, Date date);
    List<Appointment> findByUserId(long userId, Pageable pageable);
    List<Appointment> findByUserIdAndStatus(long userId, Status status, Pageable pageable);
    Appointment findByAppointmentIdAndUserId(long appointmentId, long userId);
    List<Appointment> findByDoctorIdAndDayEquals(long doctorId, Date date);

}