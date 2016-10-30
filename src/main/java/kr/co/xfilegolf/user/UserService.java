package kr.co.xfilegolf.user;

import kr.co.xfilegolf.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(UserRegisterForm userRegisterForm) {

        User user = new User();

        if (null != userRegisterForm.getId()) {

            user.setId(userRegisterForm.getId());
            user.setLastModifiedOn(LocalDateTime.now()); // 수정시..

        }

        if (null != userRegisterForm.getId() && SecurityUtils.hasAdminRole()){

            user.setPassword(userRepository.findOne(userRegisterForm.getId()).getPassword()); // 기존 비밀번호 적용..

        } else {

            user.setPassword(encodePassword(userRegisterForm.getPassword()));
        }

        user.setLoginId(userRegisterForm.getLoginId());
        user.setRoles(Arrays.asList(new Role(userRegisterForm.getRole())));
        user.setAgencyName(userRegisterForm.getAgencyName());
        user.setPresidentName(userRegisterForm.getPresidentName());
        user.setPersonInCharge(userRegisterForm.getPersonInCharge());
        user.setAddress(userRegisterForm.getAddress());
        user.setAgencyAddress(userRegisterForm.getAgencyAddress());
        user.setBusinessNumber(userRegisterForm.getBusinessNumber());
        user.setPhoneNumber(userRegisterForm.getPhoneNumber());
        user.setActivation(userRegisterForm.isActivation());

        userRepository.save(user);
    }

    private String encodePassword(String rawPassword) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public void save(UserModifyForm userModifyForm) {

        User user = userRepository.findOne(userModifyForm.getId());

        user.setPassword(encodePassword(userModifyForm.getPassword()));
        user.setAgencyName(userModifyForm.getAgencyName());
        user.setPresidentName(userModifyForm.getPresidentName());
        user.setPersonInCharge(userModifyForm.getPersonInCharge());
        user.setAgencyAddress(userModifyForm.getAgencyAddress());
        user.setBusinessNumber(userModifyForm.getBusinessNumber());
        user.setPhoneNumber(userModifyForm.getPhoneNumber());
    }

    public void remove(Long id) {

        userRepository.delete(id);
    }

    public boolean verifyPassword(String rawPassword) {

        String loginId = SecurityUtils.currentUserLoginId();

        Optional<User> user = Optional.ofNullable(userRepository.findByLoginId(loginId));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!user.isPresent()) {

            return false;
        } else {

            return encoder.matches(rawPassword, user.get().getPassword());
        }
    }

    public List<User> findByRolesIn(String role) {
        return userRepository.findByRolesRoleIn(role);
    }
}
