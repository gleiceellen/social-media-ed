package com.javagirls.social_media_ed.feed;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class FeedInitializer {

    private final Feed feed;

    public FeedInitializer(Feed feed) {
        this.feed = feed;
    }

    @PostConstruct
    public void init() {
        feed.carregarDoBanco();
        System.out.println("Feed inicializado com " + feed.getTamanho() + " postagens");
    }
}
