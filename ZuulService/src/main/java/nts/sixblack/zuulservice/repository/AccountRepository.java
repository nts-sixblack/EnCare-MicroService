package nts.sixblack.zuulservice.repository;

import nts.sixblack.zuulservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByPhone(String phone);
    Account findByAccountId(long accountId);

}