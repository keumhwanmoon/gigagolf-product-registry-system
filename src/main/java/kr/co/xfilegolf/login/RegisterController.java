package kr.co.xfilegolf.login;

import kr.co.xfilegolf.user.User;
import kr.co.xfilegolf.user.UserForm;
import kr.co.xfilegolf.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author jason, Moon
 * @since 2016-10-06
 */
@Controller
public class RegisterController {

    private RegisterService registerService;

    private UserService userService;

    @Autowired
    public RegisterController(RegisterService registerService, UserService userService) {
        this.registerService = registerService;
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public ModelAndView register(UserForm userForm) {

        return new ModelAndView("register");
    }

    @PostMapping(value = "/register")
    public String registerProcess(@Valid UserForm userForm, BindingResult result) {

        Optional<User> user = userService.findUserByLoginId(userForm.getLoginId());

        if (user.isPresent()) {

            FieldError fieldError = new FieldError("userForm", "loginId", "이미 등록된 로그인ID입니다.");

            result.addError(fieldError);

            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        registerService.registerUser(userForm);

        return "redirect:/register-success";
    }

    @GetMapping(value = "/register-success")
    public String registerSuccess() {
        return "register-success";
    }
}
