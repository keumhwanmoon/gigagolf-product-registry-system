package kr.co.xfilegolf.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @GetMapping(value = "/user-register")
    public String userRegisterPage(@RequestParam(name = "id", required = false, defaultValue = "0") Long id, UserForm userForm) {

        if (0L != id) {

            User user = userService.findOne(id);

            userForm.setId(user.getId());
            userForm.setLoginId(user.getLoginId());

            if (user.getRoles().stream().filter(role -> role.getRole().equals("ROLE_ADMIN")).findAny().isPresent()) {
                userForm.setRole("ROLE_ADMIN");
            } else {
                userForm.setRole("ROLE_USER");
            }

            userForm.setAgencyName(user.getAgencyName());
            userForm.setPresidentName(user.getPresidentName());
            userForm.setAgencyAddress(user.getAgencyAddress());
            userForm.setBusinessNumber(user.getBusinessNumber());
            userForm.setPhoneNumber(user.getPhoneNumber());
            userForm.setMobilePhoneNumber(user.getMobilePhoneNumber());
            userForm.setActivation(user.isActivation());
        } else {

            userForm.setActivation(true);
        }

        return "user-register";
    }

    @PostMapping(value = "/user-register")
    public String userRegister(@Valid UserForm userForm, BindingResult result) {

        if (null == userForm.getId()) {

            Optional<User> user = userService.findUserByLoginId(userForm.getLoginId());

            if (user.isPresent()) {

                FieldError fieldError = new FieldError("userForm", "loginId", "이미 등록된 로그인ID입니다.");

                result.addError(fieldError);

                return "user-register";
            }
        }

        if (result.hasErrors()) {
            return "user-register";
        }

        userService.save(userForm);

        return "redirect:/user";
    }

    @DeleteMapping(value = "/user")
    public void userDelete(@RequestParam(name = "id") Long id) {

        userService.remove(id);
    }
}
