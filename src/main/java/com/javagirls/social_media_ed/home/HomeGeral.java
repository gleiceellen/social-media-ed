package com.javagirls.social_media_ed.home;

import com.javagirls.social_media_ed.feed.Feed;
import com.javagirls.social_media_ed.feed.FeedDTO;
import com.javagirls.social_media_ed.usuario.UsuarioDTO;

import java.util.List;

public class HomeGeral {
    String mensagem;
    List<UsuarioDTO> usuarios;
    FeedDTO feed;

    public HomeGeral(String mensagem, List<UsuarioDTO> usuarios, Feed feed) {
        this.mensagem = mensagem;
        this.usuarios = usuarios;
        this.feed = feed.toDTO(); // Converter para DTO
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public FeedDTO getFeed() {
        return feed;
    }

    public void setFeed(FeedDTO feed) {
        this.feed = feed;
    }
}