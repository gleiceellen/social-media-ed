package com.javagirls.social_media_ed.feed;

import com.javagirls.social_media_ed.postagem.Postagem;
import java.util.List;

public class FeedDTO {
    private List<Postagem> postagens;
    private int tamanho;

    public FeedDTO(List<Postagem> postagens, int tamanho) {
        this.postagens = postagens;
        this.tamanho = tamanho;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Postagem> postagens) {
        this.postagens = postagens;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}