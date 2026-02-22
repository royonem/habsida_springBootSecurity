package web.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public HomeController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user, @RequestParam(required = false) String adminCode) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user, adminCode);
        return "redirect:/";
    }
}
