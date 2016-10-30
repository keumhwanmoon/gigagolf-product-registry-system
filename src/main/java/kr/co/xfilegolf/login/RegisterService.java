package kr.co.xfilegolf.login;

import kr.co.xfilegolf.user.Role;
import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserRegisterForm;
import kr.co.xfilegolf.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author jason, Moon
 * @since 2016-10-07
 */
@Service
public class RegisterService {

    private UserService userService;

    @Autowired
    public RegisterService(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(UserRegisterForm userRegisterForm) {

        User user = new User();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setLoginId(userRegisterForm.getLoginId());
        user.setRoles(Arrays.asList(new Role(userRegisterForm.getRole())));
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterForm.getPassword()));
        user.setAgencyName(userRegisterForm.getAgencyName());
        user.setPresidentName(userRegisterForm.getPresidentName());
        user.setAgencyAddress(userRegisterForm.getAgencyAddress());
        user.setBusinessNumber(userRegisterForm.getBusinessNumber());
        user.setPhoneNumber(userRegisterForm.getPhoneNumber());
        user.setActivation(false);
        user.setPersonInCharge(userRegisterForm.getPersonInCharge());
        user.setAddress(userRegisterForm.getAddress());

        userService.save(user);
    }
}
