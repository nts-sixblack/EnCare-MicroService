package nts.sixblack.otpservice.service;

import nts.sixblack.otpservice.form.NewPasswordFormForget;
import nts.sixblack.otpservice.form.OTPForm;
import nts.sixblack.otpservice.model.Account;
import nts.sixblack.otpservice.repository.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean existAccountByPhone(String phone) {
        try {
            List<Account> account = accountDao.findAccountByPhone(phone);
            if (account.size() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public void newOTP(String phone, String otp) {
        Account account = accountDao.findAccountByPhone(phone).get(0);
        account.setOtpCode(otp);
        accountDao.save(account);
    }

    public boolean confirmOTP(OTPForm otpForm) {
        List<Account> account = accountDao.findAccountByPhone(otpForm.getPhone());
        return otpForm.getOtp().equals(account.get(0).getOtpCode());
    }

    public boolean newPassowrd(NewPasswordFormForget newPasswordFormForget) {
        List<Account> account = accountDao.findAccountByPhone(newPasswordFormForget.getPhone());
        if (account.size() > 0) {
            account.get(0).setOtpCode(null);
            account.get(0).setPassword(passwordEncoder.encode(newPasswordFormForget.getPassword()));
            accountDao.save(account.get(0));
            return true;
        }
        return false;

    }
}
