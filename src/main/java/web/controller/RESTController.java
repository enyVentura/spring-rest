package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
public class RESTController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/authUser")
    public ResponseEntity<User> getAuthUser() {
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("admin/allUsers")
    public ResponseEntity<List<User>> userList() {
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

    @PostMapping("admin/add")
    public ResponseEntity<User> newUser(@RequestBody User user) {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("admin/edit")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("admin/edit/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
