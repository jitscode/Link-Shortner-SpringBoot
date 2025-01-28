package com.jitstcode.link_shortner_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

    @GetMapping("/auth/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/auth/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @PostMapping("/auth/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Invalidate the session
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/auth/login";
    }
}
