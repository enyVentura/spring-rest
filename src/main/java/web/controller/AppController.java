package web.controller;
/**
 * Controller initializer
 * @author Eugene Kashitsyn
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class AppController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(ModelMap model) {
        List<User> list = userService.findAll();
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("allUsers", list);
        model.addAttribute("addUser", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String userInfo(Model model) {
        UserDetails userDetails = userService.loadUserByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        model.addAttribute("userData", userService.findByUserName(userDetails.getUsername()));
        return "user";
    }


    @PostMapping(value = "/admin")
    public String addUserBd(@ModelAttribute("addUser") User user,
                            @RequestParam(value = "select_role", required = false) String[] role) {
        userService.update(user,role);
        return "redirect:/admin";
    }


    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "select_roles", required = false) String[] role) {
        userService.update(user, role);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
