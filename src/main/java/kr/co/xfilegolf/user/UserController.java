package kr.co.xfilegolf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jason, Moon
 * @since 2016-10-08
 */
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ModelAndView userPage() {

        ModelAndView mv = new ModelAndView("user");

        List<User> users = userService.findAll();

        List<UserDTO> userDTOList = users.stream().map(user -> {

            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());

            if (user.getRoles().stream().filter(role -> role.getRole().equals("ROLE_ADMIN")).findAny().isPresent()) {
                userDTO.setRole("관리자");
            } else {
                userDTO.setRole("대리점");
            }

            userDTO.setAgencyName(user.getAgencyName());
            userDTO.setPresidentName(user.getPresidentName());
            userDTO.setAgencyAddress(user.getAgencyAddress());
            userDTO.setBusinessNumber(user.getBusinessNumber());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setMobilePhoneNumber(user.getMobilePhoneNumber());
            userDTO.setCreatedOn(user.getCreatedOn());
            userDTO.setLastModifiedOn(user.getLastModifiedOn());
            userDTO.setActivation(user.isActivation());

            return userDTO;
        }).collect(Collectors.toList());

        mv.addObject("userDTOList", userDTOList);

        return mv;
    }
}
