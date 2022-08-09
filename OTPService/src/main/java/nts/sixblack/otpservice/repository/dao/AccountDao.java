package nts.sixblack.otpservice.repository.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDao {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAccountByPhone(String phone) {
        return accountRepository.findAccountByPhone(phone);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
