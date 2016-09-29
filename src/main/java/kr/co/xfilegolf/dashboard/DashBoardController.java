package kr.co.xfilegolf.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Controller
@RequestMapping
public class DashBoardController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
