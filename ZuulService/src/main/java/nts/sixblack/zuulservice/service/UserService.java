package nts.sixblack.zuulservice.service;

import nts.sixblack.zuulservice.form.RegisterFormUser;
import nts.sixblack.zuulservice.model.Account;
import nts.sixblack.zuulservice.model.User;
import nts.sixblack.zuulservice.repository.UserRepository;
import nts.sixblack.zuulservice.repository.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    public boolean register(RegisterFormUser registerFormUser) {
        Account account = accountService.registerUser(registerFormUser);

        if (account != null){
            System.out.println(account.getAccountId());
            User user = new User();
            user.setAccountId(account.getAccountId());

            userRepository.save(user);
            System.out.println();
            return true;
        }
        System.out.println("account response from account service is null");
        return false;
    }
}
