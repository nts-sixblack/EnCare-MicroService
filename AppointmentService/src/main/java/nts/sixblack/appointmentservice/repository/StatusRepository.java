package nts.sixblack.appointmentservice.repository;

import nts.sixblack.appointmentservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusId(long statusId);
}