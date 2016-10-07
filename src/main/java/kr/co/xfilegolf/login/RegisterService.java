package kr.co.xfilegolf.login;

import kr.co.xfilegolf.user.Role;
import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserDTO;
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

    public void registerUser(UserDTO userDTO) {

        User user = new User();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setLoginId(userDTO.getLoginId());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAgencyName(userDTO.getAgencyName());
        user.setPresidentName(userDTO.getPresidentName());
        user.setAgencyAddress(userDTO.getAgencyAddress());
        user.setBusinessNumber(userDTO.getBusinessNumber());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setMobilePhoneNumber(userDTO.getMobilePhoneNumber());
        user.setActivation(false);
        user.setRoles(Arrays.asList(new Role(userDTO.getRole())));

        userService.save(user);
    }
}
