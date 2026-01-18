package com.javagirls.social_media_ed;

import com.javagirls.social_media_ed.feed.Feed;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class FeedTest {

    private Feed feed;
    private Usuario author;

    @BeforeEach
    void setUp() {
        feed = new Feed();
        author = mock(Usuario.class);
        when(author.getNomeUsuario()).thenReturn("Test User");
    }
}