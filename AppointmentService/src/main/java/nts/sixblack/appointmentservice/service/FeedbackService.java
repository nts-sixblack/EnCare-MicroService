package nts.sixblack.appointmentservice.service;

import nts.sixblack.appointmentservice.form.FeedbackForm;
import nts.sixblack.appointmentservice.form.UpdateRatingForm;
import nts.sixblack.appointmentservice.model.Appointment;
import nts.sixblack.appointmentservice.model.Feedback;
import nts.sixblack.appointmentservice.repository.dao.AppointmentDao;
import nts.sixblack.appointmentservice.repository.dao.FeedbackDao;
import nts.sixblack.appointmentservice.response.FeedbackResponse;
import nts.sixblack.appointmentservice.util.RestfulData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private RestfulData restfulData;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private AppointmentDao appointmentDao;

    public boolean newFeedback(FeedbackForm feedbackForm) {
        if (checkFeedback(feedbackForm.getAppointmentId())){
            Appointment appointment = new Appointment(feedbackForm.getAppointmentId());
            Feedback feedback = new Feedback();
            feedback.setRating(Math.round(feedbackForm.getRating()));
            feedback.setComment(feedbackForm.getComment().trim());
            feedback.setUserId(feedbackForm.getUserId());
            feedback.setAppointment(appointment);
            feedbackDao.save(feedback);

            UpdateRatingForm updateRating = new UpdateRatingForm(
                    appointmentDao.findById(feedbackForm.getAppointmentId()).getDoctorId(),
                    feedbackForm.getRating()
            );

            restfulData.updateRatingDoctor(updateRating);
            return true;
        }
        return false;
    }

    private boolean checkFeedback(long appointmentId){
        Appointment appointment = new Appointment(appointmentId);
        Feedback feedback = feedbackDao.findByAppointment(appointment);
        if (feedback == null){
            return true;
        }
        return false;
    }

    public boolean existFeedback(long accountId) {
        return feedbackDao.existAccount(accountId);
    }

    public FeedbackResponse findById(long feedbackId) {
        Feedback feedback = feedbackDao.findById(feedbackId);
        if (feedback == null) {
            return null;
        }
        return transformData(feedback);
    }

    private FeedbackResponse transformData(Feedback feedback){
        FeedbackResponse feedbackResponse = new FeedbackResponse();

        feedbackResponse.setFeedbackId(feedback.getFeedbackId());
        feedbackResponse.setComment(feedback.getComment());
        feedbackResponse.setRating(feedback.getRating());

        return feedbackResponse;
    }
}
