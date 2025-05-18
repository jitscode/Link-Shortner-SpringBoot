package com.jitstcode.link_shortner_app;

import com.jitstcode.link_shortner_app.Utils.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class LinkService {
    @Value("${default-url}")
    String defaultUrl;

    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private UrlValidator urlValidator;

    private final Random random = new Random();

    public Link generateShortLink(String originalUrl, User user) {
        if(urlValidator.isValidUrl(originalUrl)){
            String shortUrl = generateUniqueShortUrl();
            Link link = new Link(originalUrl, shortUrl, user);
            return linkRepository.save(link);
        }
        else {
            return null;
        }

    }

    public void updateLink(Long id, String newUrl) {
        if(urlValidator.isValidUrl(newUrl)) {
            Optional<Link> optionalLink = linkRepository.findById(id);
            if (optionalLink.isPresent()) {
                Link link = optionalLink.get();
                link.setOriginalUrl(newUrl);
                linkRepository.save(link);
            }
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
