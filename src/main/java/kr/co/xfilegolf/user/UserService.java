package kr.co.xfilegolf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-05
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByLoginId(String loginId) {
        return Optional.ofNullable(userRepository.findByLoginId(loginId));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {

        return userRepository.findAll();
    }
}
