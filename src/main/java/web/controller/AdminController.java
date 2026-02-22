package web.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AdminController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // user list
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    // create form
    @GetMapping("/new-user")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    // update form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.get(id));
        return "user-form";
    }

    // create or update
    @PostMapping("/save")
    public String save(@ModelAttribute User user, @RequestParam(required = false) String adminCode) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user, adminCode);
        return "redirect:/admin";
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
