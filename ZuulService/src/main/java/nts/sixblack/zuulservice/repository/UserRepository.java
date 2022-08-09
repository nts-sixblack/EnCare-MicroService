package nts.sixblack.zuulservice.repository;

import nts.sixblack.zuulservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}