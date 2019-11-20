package pres.menta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 13:33 2019/11/17
 */
@Controller
public class LoginRegisterHandler {

    @RequestMapping("/index")
    public String index()
    {
        System.out.println("/index");
        return "index";
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     */
    @RequestMapping("/login_page")
    public String loginPage() {
        System.out.println("/login_page");
        return "login_page";
    }

}