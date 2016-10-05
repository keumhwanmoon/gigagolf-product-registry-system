package kr.co.xfilegolf.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jason, Moon
 * @since 2016. 9. 29.
 */
@Controller
@RequestMapping
public class ProductController {

    @RequestMapping(value = "/product")
    public ModelAndView page() {
        return new ModelAndView("product");
    }
}
