package nts.sixblack.appointmentservice.repository.dao;

import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Feedback;
import nts.sixblack.appointmentservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDao {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback findByAppointment(Appointment appointment) {
        return feedbackRepository.findByAppointment(appointment);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public boolean existAccount(long accountId) {
        return feedbackRepository.existsById(accountId);
    }

    public Feedback findById(long feedbackId) {
        try {
            Feedback feedback = feedbackRepository.findByFeedbackId(feedbackId);
            if (feedback == null) {
                return null;
            }
            return feedback;
        } catch (Exception e) {
            return null;
        }
    }
}
