package nts.sixblack.patientservice.service;

import nts.sixblack.patientservice.model.User;
import nts.sixblack.patientservice.repository.dao.UserDao;
import nts.sixblack.patientservice.response.AccountResponse;
import nts.sixblack.patientservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserResponse findById(long userId) {
        User user = userDao.findByUserId(userId);
        if (user != null) {
            return transformData(user);
        }
        return null;
    }

    private UserResponse transformData(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setAccountResponse(new AccountResponse(user.getAccountId()));

        return userResponse;
    }

    public boolean existAccount(long userId) {
        return userDao.existAccount(userId);
    }

    public UserResponse findByAccountUserId(long accountUserID) {
        User user = userDao.findByAccountUserId(accountUserID);
        if (user == null) {
            return null;
        }
        return transformData(user);
    }
}
