package com.jitstcode.link_shortner_app;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Link {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    @Column(unique = true)
    private String shortUrl;

    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Link() {}

    public Link(String originalUrl, String shortUrl, User user) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }
}
