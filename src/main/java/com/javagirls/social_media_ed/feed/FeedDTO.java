package com.javagirls.social_media_ed.feed;

import com.javagirls.social_media_ed.postagem.PostagemNoDTO;

import java.util.List;

public class FeedDTO {
    private String mensagem;
    private List<PostagemNoDTO> postagens;
    private int tamanho;

    public FeedDTO(List<PostagemNoDTO> postagens, int tamanho) {
        this.mensagem = "Bem-vindo ao Feed!";
        this.postagens = postagens;
        this.tamanho = tamanho;
    }

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<PostagemNoDTO> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<PostagemNoDTO> postagens) {
        this.postagens = postagens;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}