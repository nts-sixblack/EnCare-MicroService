package nts.sixblack.appointmentservice.service;

import nts.sixblack.appointmentservice.repository.dao.StatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusDao statusDao;

    public String getStatus(long statusId) {
        return statusDao.findById(statusId).getName();
    }
}
