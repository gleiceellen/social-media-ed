package com.javagirls.social_media_ed;

import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;

class PostagemTest {

    private Usuario author;
    private Postagem postagem;

    @BeforeEach
    void setUp() {
        author = new Usuario(1, "Author", "author@email.com");
        postagem = new Postagem("Mensagem do post", author);
    }
}