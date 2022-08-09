package nts.sixblack.patientservice.repository;

import nts.sixblack.patientservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(long userId);
    User findByAccountId(long accountUserId);
}