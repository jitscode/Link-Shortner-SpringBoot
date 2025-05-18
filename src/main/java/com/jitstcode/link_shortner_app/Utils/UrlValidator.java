package com.jitstcode.link_shortner_app.Utils;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

@Component
public class UrlValidator {

    private static final String URL_REGEX = "^(https?|ftp)://[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,})?(:\\d+)?(/.*)?$";

    private final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        // Check URL pattern
        if (!URL_PATTERN.matcher(url).matches()) {
            return false;
        }

        // Check if URL can be parsed
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
