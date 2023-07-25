package niit.com.springboot08.controller;

import jakarta.servlet.http.HttpSession;
import niit.com.springboot08.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("demoSessionController")
public class DemoSessionController {
    @GetMapping("/set-session")
    public String setSession(HttpSession httpSession) {
        User user = new User();
        user.setName("Nguyen Thi Thuy Dung");
        user.setAddress("Ha Noi");
        user.setAge(20);
        httpSession.setAttribute("user", user);
        return "set-session";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        return "get-session";
    }

    @GetMapping("/delete-session")
    public String deleteSession(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "set-session";
    }
}
