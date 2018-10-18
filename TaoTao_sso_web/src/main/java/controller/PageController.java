package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/19 0019 18:49
 * 4
 */
@Controller
public class PageController {
    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/page/login")
    public String showLogin(String url, Model model) {
        model.addAttribute("redirect", url);
        return "login";
    }
}
