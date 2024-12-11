package ru.kata.springboot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.springboot_security.demo.models.Role;
import ru.kata.springboot_security.demo.models.User;
import ru.kata.springboot_security.demo.services.RoleService;
import ru.kata.springboot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AdminController {
    private static final String REDIRECT = "redirect:/admin-page";
    private UserService userService;
    private RoleService roleService;

    @GetMapping("/admin-page")
    public String getAllUsers(Authentication auth, ModelMap model) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
        List<Role> roles = roleService.getAllRoles();
        List<User> users = userService.getAllUsers();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/add-new-user")
    public String addNewUserForm(User newUser) {
        userService.saveUser(newUser);
        return REDIRECT;
    }

    @PostMapping("/edit-form")
    public String editUser(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("roles") Set<Role> roles) {
        userService.updateUser(id, username, email, password, roles);
        return REDIRECT;
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return REDIRECT;
    }
}
