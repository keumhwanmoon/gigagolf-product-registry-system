package kr.co.xfilegolf.user;

import kr.co.xfilegolf.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;
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

        ModelAndView mv = new ModelAndView("/user/user");

        List<User> users = userService.findAll();

        List<UserDTO> userDTOList = users.stream().map(user -> {

            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());

            if (user.getRoles().stream().filter(role -> role.getRole().equals("ROLE_ADMIN")).findAny().isPresent()) {

                userDTO.setRole("관리자");
            } else {
                userDTO.setRole("대리점");
            }

            userDTO.setLoginId(user.getLoginId());
            userDTO.setAgencyName(user.getAgencyName());
            userDTO.setPresidentName(user.getPresidentName());
            userDTO.setPersonInCharge(user.getPersonInCharge());
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
            userForm.setPersonInCharge(user.getPersonInCharge());
            userForm.setAgencyAddress(user.getAgencyAddress());
            userForm.setBusinessNumber(user.getBusinessNumber());
            userForm.setPhoneNumber(user.getPhoneNumber());
            userForm.setMobilePhoneNumber(user.getMobilePhoneNumber());
            userForm.setActivation(user.isActivation());
        } else {

            userForm.setActivation(true);
        }

        return "/user/user-register";
    }

    @PostMapping(value = "/user-register")
    public String userRegister(@Valid UserForm userForm, BindingResult result) {

        if (null == userForm.getId()) {

            Optional<User> user = userService.findUserByLoginId(userForm.getLoginId());

            if (user.isPresent()) {

                FieldError fieldError = new FieldError("userForm", "loginId", "이미 등록된 로그인ID입니다. : " + userForm.getLoginId());

                result.addError(fieldError);

                return "/user/user-register";
            }
        }

        if (!SecurityUtils.hasAdminRole() && result.hasErrors()) {
            return "/user/user-register";
        } else if (SecurityUtils.hasAdminRole() && result.getErrorCount() > 1) {
            return "/user/user-register";
        } else if (SecurityUtils.hasAdminRole() && result.getErrorCount() == 1 && !result.hasFieldErrors("password")) {
            return "/user/user-register";
        }

        userService.save(userForm);

        return "redirect:/user";
    }

    @DeleteMapping(value = "/user")
    public String userDelete(@RequestParam(name = "id") Long id) {

        userService.remove(id);

        return "/user/user";
    }

    @GetMapping(value = "/update")
    public ModelAndView userUpdate() {

        ModelAndView mv = new ModelAndView("/user/update");

        mv.addObject("personInCharge", SecurityUtils.currentPersonInCharge());

        return mv;
    }

    @GetMapping(value = "/verifyPassword")
    @ResponseBody
    public Map<String, Object> verifyPassword(@RequestParam(value = "password") String password) {

        return Collections.singletonMap("success", userService.verifyPassword(password));
    }

    @GetMapping(value = "/user-modify")
    public ModelAndView userModify(UserModifyForm userModifyForm) {

        ModelAndView mv = new ModelAndView("/user/user-modify");

        mapToForm(userModifyForm);

        return mv;
    }

    @PostMapping(value = "/user-modify")
    public String userModify(@Valid UserModifyForm userModifyForm, BindingResult result) {

        if (result.hasErrors()) {
            return "/user/user-modify";
        }

        userService.save(userModifyForm);

        return "redirect:/";
    }

    private void mapToForm(UserModifyForm userModifyForm) {

        User user = SecurityUtils.currentUser();

        userModifyForm.setId(user.getId());
        userModifyForm.setAgencyName(user.getAgencyName());
        userModifyForm.setPresidentName(user.getPresidentName());
        userModifyForm.setPersonInCharge(user.getPersonInCharge());
        userModifyForm.setAgencyAddress(user.getAgencyAddress());
        userModifyForm.setBusinessNumber(user.getBusinessNumber());
        userModifyForm.setPhoneNumber(user.getPhoneNumber());
        userModifyForm.setMobilePhoneNumber(user.getMobilePhoneNumber());
    }
}
