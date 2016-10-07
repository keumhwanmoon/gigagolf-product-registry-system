package kr.co.xfilegolf.login;

import kr.co.xfilegolf.user.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author jason, Moon
 * @since 2016-10-06
 */
@Controller
public class RegisterController {

    @GetMapping(value = "/register")
    public ModelAndView register(UserDTO userDTO) {

        return new ModelAndView("register");
    }

    @PostMapping(value = "/register")
    public String registerProcess(@Valid UserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        return "redirect:/register-success";
    }
}
