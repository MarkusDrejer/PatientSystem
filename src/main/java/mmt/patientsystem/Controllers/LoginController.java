package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String loginPage(){
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpSession httpSession, @ModelAttribute User user, Model model) throws SQLException {

        if(loginService.verifyUser(user)) {
            httpSession.setAttribute("id", user.getId());
            httpSession.setAttribute("name", user.getName());
            httpSession.setAttribute("roleTier", user.getRole_id());
            httpSession.setAttribute("role_name", user.getRole_name());
            return "home";
        } else {
            model.addAttribute("invalid", true);
            return "index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
