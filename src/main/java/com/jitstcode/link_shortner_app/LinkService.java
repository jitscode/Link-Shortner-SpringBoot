package com.jitstcode.link_shortner_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
    @Value("${default-url}")
    String defaultUrl;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    private final Random random = new Random();

    public Link generateShortLink(String originalUrl, User user) {
        String shortUrl = generateUniqueShortUrl();
        Link link = new Link(originalUrl, shortUrl, user);
        return linkRepository.save(link);
    }

    public void updateLink(Long id, String newUrl) {
        Optional<Link> optionalLink = linkRepository.findById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            link.setOriginalUrl(newUrl);
            linkRepository.save(link);
        }
    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }

    public List<Link> getLinksByUser(User user) {
        return linkRepository.findByUser(user);
    }

    private String generateUniqueShortUrl() {
        String shortUrl;
        do {
            shortUrl = generateRandomString(6);
        } while (linkRepository.findByShortUrl(shortUrl).isPresent());
        return "r/"+shortUrl;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            shortUrl.append(characters.charAt(index));
        }
        return shortUrl.toString();
    }
}
