package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @GetMapping("/")
    public String loginPage(){
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpSession httpSession, @ModelAttribute User user, Model model) throws SQLException {

    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
