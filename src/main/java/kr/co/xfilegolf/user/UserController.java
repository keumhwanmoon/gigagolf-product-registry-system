package kr.co.xfilegolf.user;

import kr.co.xfilegolf.SecurityUtils;
import kr.co.xfilegolf.sale.Sale;
import kr.co.xfilegolf.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final SaleService saleService;

    @Autowired
    public UserController(UserService userService, SaleService saleService) {
        this.userService = userService;
        this.saleService = saleService;
    }

    @GetMapping(value = "/user")
    public ModelAndView userPage(@RequestParam(name = "role", required = false) String role) {

        ModelAndView mv = new ModelAndView("/user/user");

        List<User> users;

        if (StringUtils.isEmpty(role)) {
            users = userService.findAll();
        } else {
            users = userService.findByRolesIn(role);
        }

        List<UserDTO> userDTOList = users.stream().map(user -> {

            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());

            if (user.getRoles().stream().filter(r -> r.getRole().equals("ROLE_ADMIN")).findAny().isPresent()) {

                userDTO.setRole("관리자");
                userDTO.setAgencyAddress(user.getAgencyAddress());
            } else if (user.getRoles().stream().filter(r -> r.getRole().equals("ROLE_AGENCY")).findAny().isPresent()) {

                userDTO.setRole("대리점");
                userDTO.setAgencyAddress(user.getAgencyAddress());
            } else {

                userDTO.setRole("개인");
                userDTO.setAgencyAddress(user.getAddress()); // 개인사용자의 주소 표시를 위해
            }

            userDTO.setLoginId(user.getLoginId());
            userDTO.setAgencyName(user.getAgencyName());
            userDTO.setPresidentName(user.getPresidentName());
            userDTO.setPersonInCharge(user.getPersonInCharge());
            userDTO.setBusinessNumber(user.getBusinessNumber());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setCreatedOn(user.getCreatedOn());
            userDTO.setLastModifiedOn(user.getLastModifiedOn());
            userDTO.setActivation(user.isActivation());

            return userDTO;
        }).collect(Collectors.toList());

        mv.addObject("userDTOList", userDTOList);

        return mv;
    }

    @GetMapping(value = "/user-register")
    public String userRegisterPage(@RequestParam(name = "id", required = false, defaultValue = "0") Long id, UserRegisterForm userRegisterForm) {

        if (0L != id) {

            User user = userService.findOne(id);

            userRegisterForm.setId(user.getId());
            userRegisterForm.setLoginId(user.getLoginId());

            setUserRole(userRegisterForm, user);

            userRegisterForm.setAgencyName(user.getAgencyName());
            userRegisterForm.setPresidentName(user.getPresidentName());
            userRegisterForm.setPersonInCharge(user.getPersonInCharge());
            userRegisterForm.setAgencyAddress(user.getAgencyAddress());
            userRegisterForm.setBusinessNumber(user.getBusinessNumber());
            userRegisterForm.setPhoneNumber(user.getPhoneNumber());
            userRegisterForm.setActivation(user.isActivation());
            userRegisterForm.setAddress(user.getAddress());
        } else {

            userRegisterForm.setActivation(true);
        }

        return "/user/user-register";
    }

    @PostMapping(value = "/user-register")
    public String userRegister(@Valid UserRegisterForm userRegisterForm, BindingResult result, RedirectAttributes redirectAttributes) {

        if (null == userRegisterForm.getId()) {

            Optional<User> user = userService.findUserByLoginId(userRegisterForm.getLoginId());

            if (user.isPresent()) {

                FieldError fieldError = new FieldError("userRegisterForm", "loginId", "이미 등록된 로그인ID입니다. : " + userRegisterForm.getLoginId());

                result.addError(fieldError);

                return "/user/user-register";
            }
        }

        result = UserValidator.validation(userRegisterForm, result);

        if (!SecurityUtils.hasAdminRole() && result.hasErrors()) {
            return "/user/user-register";
        } else if (SecurityUtils.hasAdminRole() && result.getErrorCount() > 1) {
            return "/user/user-register";
        } else if (SecurityUtils.hasAdminRole() && result.getErrorCount() == 1 && !result.hasFieldErrors("password")) {
            return "/user/user-register";
        }

        userService.save(userRegisterForm);

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/user-register";
    }

    @DeleteMapping(value = "/user")
    public String userDelete(@RequestParam(name = "id") Long id) throws Exception {

        User user = userService.findOne(id);

        String loginId = user.getLoginId();

        List<Sale> sales = saleService.findByLoginId(loginId);

        if (!sales.isEmpty()) {
            throw new Exception("등록된 판매/구매 내역이 존재하여 사용자를 삭제할 수 없습니다.");
        } else {
            userService.remove(id);
        }

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

        setUserRole(userModifyForm, user);

        userModifyForm.setAgencyName(user.getAgencyName());
        userModifyForm.setPresidentName(user.getPresidentName());
        userModifyForm.setPersonInCharge(user.getPersonInCharge());
        userModifyForm.setAgencyAddress(user.getAgencyAddress());
        userModifyForm.setBusinessNumber(user.getBusinessNumber());
        userModifyForm.setPhoneNumber(user.getPhoneNumber());
        userModifyForm.setAddress(user.getAddress());
    }

    private void setUserRole(UserForm userForm, User user) {
        if (user.getRoles().stream().filter(role -> role.getRole().equals("ROLE_ADMIN")).findAny().isPresent()) {
            userForm.setRole("ROLE_ADMIN");
        } else if (user.getRoles().stream().filter(role -> role.getRole().equals("ROLE_USER")).findAny().isPresent()) {
            userForm.setRole("ROLE_USER");
        } else {
            userForm.setRole("ROLE_AGENT");
        }
    }
}
