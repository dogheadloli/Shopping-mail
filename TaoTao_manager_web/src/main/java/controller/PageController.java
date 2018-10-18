package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 9:25
 * 4    页面跳转
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showpage(@PathVariable String page) {
        return page;
    }
}
