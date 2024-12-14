package ru.kata.spring.boot_security.demo.conrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonValidator personValidator;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(PersonValidator personValidator, RoleService roleService, UserService userService) {
        this.personValidator = personValidator;
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/users")
    public String showAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("person", new User());
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "users";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@ModelAttribute("person") @Valid User user, BindingResult bindingResult) {
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users";
        }
        userService.addNewUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("showUser") @Valid User user) {
        userService.edit(user);
        return "redirect:/admin/users";
    }


    @PostMapping("/delete")
    public String deleteUserId(@ModelAttribute("showUser") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/users";
    }

}
