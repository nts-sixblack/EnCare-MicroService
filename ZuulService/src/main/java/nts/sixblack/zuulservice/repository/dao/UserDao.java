package nts.sixblack.zuulservice.repository.dao;

import nts.sixblack.zuulservice.model.User;
import nts.sixblack.zuulservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
