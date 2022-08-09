package nts.sixblack.zuulservice.repository.dao;

import nts.sixblack.zuulservice.model.Account;
import nts.sixblack.zuulservice.repository.AccountRepository;
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

    public Account findByAccountId(long id) {
        return accountRepository.findByAccountId(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public boolean existAccount(long accountId) {
        return accountRepository.existsById(accountId);
    }

}
