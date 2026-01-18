package com.javagirls.social_media_ed.feed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javagirls.social_media_ed.commons.UsuarioLogadoComponent;
import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class Feed {
    private UsuarioLogadoComponent usuarioLogadoComponent;
    private Postagem cabeca;
    private int tamanho;
    private int proximoId = 1;

    public Feed(UsuarioLogadoComponent usuarioLogadoComponent) {
        this.cabeca = null;
        this.tamanho = 0;
        this.usuarioLogadoComponent = usuarioLogadoComponent;
    }

    public void adicionarNoInicio(String mensagem) {
        Usuario autor = usuarioLogadoComponent.getUsuarioLogado();
        Postagem novaPostagem = new Postagem(mensagem, autor);
        novaPostagem.setId(proximoId++);

        if (cabeca != null) {
            novaPostagem.setProximo(cabeca);
            cabeca.setAnterior(novaPostagem);
        }

        cabeca = novaPostagem;
        tamanho++;
    }

    @JsonIgnore
    public List<Postagem> getTodasPostagens() {
        List<Postagem> postagens = new ArrayList<>();
        Postagem atual = cabeca;
        while (atual != null) {
            postagens.add(atual);
            atual = atual.getProximo();
        }
        return postagens;
    }

    public List<Postagem> getPostagens() {
        return getTodasPostagens();
    }

    public int getTamanho() {
        return tamanho;
    }

    @JsonIgnore
    public Postagem getCabeca() {
        return cabeca;
    }

    public FeedDTO toDTO() {
        return new FeedDTO(getTodasPostagens(), getTamanho());
    }
}