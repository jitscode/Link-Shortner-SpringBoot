package com.jitstcode.link_shortner_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class RedirectController {

    @Autowired
    private LinkRepository linkRepository;
    @GetMapping("/r/{shortUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortUrl) {
        Optional<Link> link = linkRepository.findByShortUrl("r/"+shortUrl);
        if (link.isPresent()) {
            return "redirect:" + link.get().getOriginalUrl();
        } else {
            return "redirect:/404";
        }
    }
}
