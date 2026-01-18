package com.javagirls.social_media_ed.home;

import com.javagirls.social_media_ed.feed.Feed;
import com.javagirls.social_media_ed.usuario.Usuario;
import com.javagirls.social_media_ed.usuario.UsuarioDTO;

import java.util.List;

public class HomeGeral {
    String mensagem;
    List<UsuarioDTO> usuarios;
    Feed feed;

    public HomeGeral(String mensagem, List<UsuarioDTO> usuarios, Feed feed) {
        this.mensagem = mensagem;
        this.usuarios = usuarios;
        this.feed = feed;
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

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
