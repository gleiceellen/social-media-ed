package com.javagirls.social_media_ed.feed;

import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.postagem.RequisicaoPostagem;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.*;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private final Feed feed;

    public FeedController(Feed feed) {
        this.feed = feed;
    }

    @PostMapping("/postagem")
    public ResponseEntity<Postagem> adicionarPostagem(
            @RequestBody RequisicaoPostagem request) {
        feed.adicionarNoInicio(request.mensagem());
        return ResponseEntity.ok().build();
    }
}