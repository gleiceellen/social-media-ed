package com.javagirls.social_media_ed.postagem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

public class CurtidasController {

    public HashSet curtidas;

    public CurtidasController(HashSet curtidas) {
        this.curtidas = curtidas;
    }

    @PostMapping("/postagem/curtidas")
    public ResponseEntity<Postagem> curtirPostagem(@RequestBody RequisicaoCurtidaPostagem requisicao) {
        //curtir
        return ResponseEntity.ok().build();
    }
}
