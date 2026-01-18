package com.javagirls.social_media_ed.feed;

import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.stereotype.Service;


@Service
public class Feed {
    private Postagem cabeca;
    private int tamanho;
    private int proximoId = 1;

    public Feed() {
        this.cabeca = null;
        this.tamanho = 0;
    }

    public void adicionarNoInicio(String mensagem, Usuario autor) {
        Postagem novaPostagem = new Postagem(mensagem, autor);
        novaPostagem.setId(proximoId++);

        if (cabeca != null) {
            novaPostagem.setIdProximo(cabeca.getId());
            cabeca.setIdAnterior(novaPostagem.getId());
        }

        cabeca = novaPostagem;
        tamanho++;
    }

    public int getTamanho() {
        return tamanho;
    }

    public Postagem getCabeca() {
        return cabeca;
    }
}