package kr.co.xfilegolf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    public List<User> findAll() {

        return userRepository.findAll();
    }

    public User findOne(Long id) {

        return userRepository.findOne(id);
    }

    public void save(User user) {

        userRepository.save(user);
    }

    public void save(UserForm userForm) {

        User user = new User();

        if (null != userForm.getId()) {

            user.setId(userForm.getId());
            user.setLastModifiedOn(LocalDateTime.now()); // 수정시..
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setLoginId(userForm.getLoginId());
        user.setRoles(Arrays.asList(new Role(userForm.getRole())));
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        user.setAgencyName(userForm.getAgencyName());
        user.setPresidentName(userForm.getPresidentName());
        user.setAgencyAddress(userForm.getAgencyAddress());
        user.setBusinessNumber(userForm.getBusinessNumber());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setMobilePhoneNumber(userForm.getMobilePhoneNumber());
        user.setActivation(userForm.isActivation());

        userRepository.save(user);
    }

    public void remove(Long id) {

        userRepository.delete(id);
    }
}
