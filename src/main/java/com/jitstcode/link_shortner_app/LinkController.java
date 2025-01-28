package com.jitstcode.link_shortner_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/shortner/dashboard")
    public String dashboard(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("links", linkService.getLinksByUser(user));
        model.addAttribute("username", user.getUsername());
        return "dashboard";
    }

    @PostMapping("/shortner/create")
    public String createLink(@RequestParam String originalUrl, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        linkService.generateShortLink(originalUrl, user);
        return "redirect:/shortner/dashboard";
    }

    @PostMapping("/shortner/update/{id}")
    public String updateLink(@PathVariable Long id, @RequestParam String newUrl) {
        linkService.updateLink(id, newUrl);
        return "redirect:/shortner/dashboard";
    }

    @PostMapping("/shortner/delete/{id}")
    public String deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return "redirect:/shortner/dashboard";
    }
}
