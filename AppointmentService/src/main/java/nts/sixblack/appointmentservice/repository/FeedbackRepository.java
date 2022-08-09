package nts.sixblack.appointmentservice.repository;

import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByFeedbackId(long feedbackId);
    Feedback findByAppointment(Appointment appointment);
}