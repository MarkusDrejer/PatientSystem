package mmt.patientsystem.Controllers;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * displays users page
     **/
    @GetMapping("/users")
    public String usersPage(Model model) {
        try {
            model.addAttribute("userList", userService.getAllUsers());
            return "UserPages/users";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * displays edit user form
     * @param id
     **/
    @GetMapping("/users/edituser/{id}")
    public String editUser(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("user", userService.getSingleUser(id));
            return "UserPages/edituser";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * saves edit user form
     * @param user
     **/
    @PostMapping("/users/edituser")
    public String editUser(@ModelAttribute User user, Model model) {
        try {
            userService.editUser(user);
            return "redirect:/users";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * deletes user
     * @param id
     **/
    @PostMapping("/users/deleteuser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id, Model model) {
        try {
            userService.deleteUser(id);
            return "redirect:/users";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }

    /**
     * displays add user form
     **/
    @GetMapping("/users/adduser")
    public String addUser() {
        return "UserPages/adduser";
    }

    /**
     * saves add user form
     * @param user
     **/
    @PostMapping("/users/adduser")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/users";

        } catch (SQLException e) {
            model.addAttribute("errorCode", e.getErrorCode());
            return "sqlerror";
        }
    }
}
