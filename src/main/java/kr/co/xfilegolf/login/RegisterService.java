package kr.co.xfilegolf.login;

import kr.co.xfilegolf.user.Role;
import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserForm;
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

    public void registerUser(UserForm userForm) {

        User user = new User();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setLoginId(userForm.getLoginId());
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        user.setAgencyName(userForm.getAgencyName());
        user.setPresidentName(userForm.getPresidentName());
        user.setAgencyAddress(userForm.getAgencyAddress());
        user.setBusinessNumber(userForm.getBusinessNumber());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setMobilePhoneNumber(userForm.getMobilePhoneNumber());
        user.setActivation(false);
        user.setRoles(Arrays.asList(new Role(userForm.getRole())));

        userService.save(user);
    }
}
