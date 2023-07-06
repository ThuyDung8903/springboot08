package niit.com.springboot08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("LoginController")
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/do-login")
    public void doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
    }
}
