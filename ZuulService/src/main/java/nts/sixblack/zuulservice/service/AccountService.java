package nts.sixblack.zuulservice.service;

import nts.sixblack.zuulservice.config.TimeConfig;
import nts.sixblack.zuulservice.form.InformationForm;
import nts.sixblack.zuulservice.form.NewPasswordForm;
import nts.sixblack.zuulservice.form.RegisterFormDoctor;
import nts.sixblack.zuulservice.form.RegisterFormUser;
import nts.sixblack.zuulservice.model.Account;
import nts.sixblack.zuulservice.repository.AccountRepository;
import nts.sixblack.zuulservice.repository.dao.AccountDao;
import nts.sixblack.zuulservice.response.AccountResponse;
import nts.sixblack.zuulservice.util.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        List<Account> accountList = accountDao.findAccountByPhone(phone);
        if (accountList == null) {
            System.out.println("null");
            throw new UsernameNotFoundException(phone);
        }
        Account account = accountList.get(0);
        return new CustomUserDetail(account);
    }

    public UserDetails getUserDetailById(long id) {
        Account account = accountDao.findByAccountId(id);
        return new CustomUserDetail(account);
    }

    public Account registerUser(RegisterFormUser registerFormUser) {
        if (findByPhone("+84"+Long.parseLong(registerFormUser.getPhone().trim()))){
            Account account = new Account();

            account.setPhone("+84"+Long.parseLong(registerFormUser.getPhone().trim()));
            account.setPassword(passwordEncoder.encode(registerFormUser.getPassword()));
            account.setRole("PATIENT");
            account.setName(registerFormUser.getName().trim());
            account.setCreateDate(new Date());

            return accountDao.save(account);
        }
        return null;
    }

    public boolean findByPhone(String phone) {
        try {
            List<Account> account = accountDao.findAccountByPhone(phone);
            if (account.size() == 0) {
                return true;
            }
            return false;
        } catch (Exception e){
            return true;
        }
    }

    public long registerDoctor(RegisterFormDoctor registerFormDoctor) {
        if (findByPhone("+84"+Long.parseLong(registerFormDoctor.getPhone().trim()))) {
            Account account = new Account();

            account.setPhone("+84"+Long.parseLong(registerFormDoctor.getPhone().trim()));
            account.setPassword(passwordEncoder.encode(registerFormDoctor.getPassword()));
            account.setRole("DOCTOR");
            account.setName(registerFormDoctor.getName().trim());
            account.setBirthday(TimeConfig.getDate(registerFormDoctor.getBirthDay()));
            account.setCreateDate(new Date());
            account.setDescription(registerFormDoctor.getDescription().trim());

            return accountDao.save(account).getAccountId();
        }
        return 0;
    }

    public boolean existAccount(long accountId) {
        return accountDao.existAccount(accountId);
    }

    public AccountResponse findById(long id) {
        Account account = accountDao.findByAccountId(id);
        if (account != null) {
            return transformData(account);
        }
        return null;
    }

    private AccountResponse transformData(Account account) {
        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setAccountId(account.getAccountId());
        accountResponse.setPhone(account.getPhone());
        accountResponse.setRole(account.getRole());
        accountResponse.setName(account.getName());
        accountResponse.setAvatar(account.getAvatar());
        accountResponse.setDescription(account.getDescription());

        if (account.getBirthday()!=null){
            accountResponse.setBirthday(TimeConfig.getTime(account.getBirthday()));
        }
        accountResponse.setCreateDate(TimeConfig.getTime(account.getCreateDate()));

        return accountResponse;
    }

    public boolean updatePassword(NewPasswordForm newPasswordForm) {
        if (checkPass(newPasswordForm.getOldPassword(), newPasswordForm.getAccountId())){
            Account account = accountDao.findByAccountId(newPasswordForm.getAccountId());
            account.setPassword(passwordEncoder.encode(newPasswordForm.getNewPassword()));
            accountDao.save(account);
            return true;
        }
        return false;
    }

    private boolean checkPass(String oldPass, long accountId){
        Account account = accountDao.findByAccountId(accountId);
        return BCrypt.checkpw(oldPass, account.getPassword());
    }

    public boolean updateInformation(InformationForm informationForm) {
        Account account = accountDao.findByAccountId(informationForm.getAccountId());

        if (!informationForm.getBirthDay().isBlank()){
            Date date = TimeConfig.getDate(informationForm.getBirthDay());
            Date now = new Date();
            if (!date.before(now)) {
                return false;
            }
            account.setBirthday(TimeConfig.getDate(informationForm.getBirthDay()));
        }
        account.setName(informationForm.getName().trim());
        account.setDescription(informationForm.getDescription().trim());

        accountDao.save(account);

        return true;
    }

}
