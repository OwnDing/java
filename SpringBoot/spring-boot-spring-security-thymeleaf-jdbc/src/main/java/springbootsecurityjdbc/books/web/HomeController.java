package springbootsecurityjdbc.books.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/book")
    public String book(){return "user/book";}

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}
