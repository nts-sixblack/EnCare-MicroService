package nts.sixblack.patientservice.repository.dao;

import nts.sixblack.patientservice.model.User;
import nts.sixblack.patientservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    public User findByUserId(long userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean existAccount(long userId) {
        return userRepository.existsById(userId);
    }

    public User findByAccountUserId(long accountUserId) {
        return userRepository.findByAccountId(accountUserId);
    }
}
