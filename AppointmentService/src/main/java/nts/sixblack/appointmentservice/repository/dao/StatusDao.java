package nts.sixblack.appointmentservice.repository.dao;

import nts.sixblack.appointmentservice.model.Status;
import nts.sixblack.appointmentservice.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusDao {

    @Autowired
    private StatusRepository statusRepository;

    public Status findById(long statusId) {
        return statusRepository.findByStatusId(statusId);
    }
}
