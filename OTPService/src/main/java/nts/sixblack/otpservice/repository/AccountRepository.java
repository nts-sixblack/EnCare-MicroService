package nts.sixblack.otpservice.repository;

import nts.sixblack.otpservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByPhone(String phone);
}