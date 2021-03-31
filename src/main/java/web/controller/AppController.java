package web.controller;
/**
 * Controller initializer
 * @author Eugene Kashitsyn
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.UserService;

@Controller
public class AppController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/home"})
    public String index() {
        return "home";
    }

    @GetMapping("/admin")
    public String userListForAdmin(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "admin";
    }

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "user";
    }

    @GetMapping(value = "/registration")
    public String addUser(ModelMap modelMap) {
        modelMap.addAttribute("addUser", new User());
        modelMap.addAttribute("allRoles", userService.getAllRoles());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String addUserById(@ModelAttribute("addUser") User user,
                            @RequestParam(value = "select_role", required = false) String[] role) {
        userService.update(user,role);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.findById(id));
        modelMap.addAttribute("allRoles", userService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "select_roles", required = false) String[] role) {
        userService.update(user,role);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
