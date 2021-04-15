package web.controller;
/**
 * Controller initializer
 * @author Eugene Kashitsyn
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @GetMapping(value = "/admin")
    public String printAdminPage() {
        return "admin";
    }

    @GetMapping("/user")
    public ModelAndView viewUserPage() {
        return new ModelAndView("user");
    }

}
