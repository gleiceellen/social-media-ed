package com.javagirls.social_media_ed.postagem;

import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.Set;

public class PostagemNo {
    private Integer id;
    private String mensagem;
    private Integer autorId;
    private String autorNome;
    private Integer contadorCurtidas;
    private LocalDateTime dataCriacao;
    private PostagemNo anterior;
    private PostagemNo proximo;

    // Construtor vazio
    public PostagemNo() {
    }

    // Construtor a partir de Postagem
    public PostagemNo(Postagem postagem) {
        this.id = postagem.getId();
        this.mensagem = postagem.getMensagem();
        this.autorId = postagem.getAutor() != null ? postagem.getAutor().getId() : null;
        this.autorNome = postagem.getAutor() != null ? postagem.getAutor().getNomeUsuario() : null;
        this.contadorCurtidas = postagem.getContadorCurtidas();
        this.dataCriacao = postagem.getDataCriacao();
    }

    // Construtor com parâmetros
    public PostagemNo(Integer id, String mensagem, Integer autorId, String autorNome,
                      Integer contadorCurtidas, LocalDateTime dataCriacao) {
        this.id = id;
        this.mensagem = mensagem;
        this.autorId = autorId;
        this.autorNome = autorNome;
        this.contadorCurtidas = contadorCurtidas;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public Integer getContadorCurtidas() {
        return contadorCurtidas;
    }

    public void setContadorCurtidas(Integer contadorCurtidas) {
        this.contadorCurtidas = contadorCurtidas;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public PostagemNo getAnterior() {
        return anterior;
    }

    public void setAnterior(PostagemNo anterior) {
        this.anterior = anterior;
    }

    public PostagemNo getProximo() {
        return proximo;
    }

    public void setProximo(PostagemNo proximo) {
        this.proximo = proximo;
    }

    // Método para converter para DTO simplificado
    public PostagemNoDTO toDTO() {
        Integer anteriorId = (this.anterior != null) ? this.anterior.getId() : null;
        Integer proximoId = (this.proximo != null) ? this.proximo.getId() : null;

        return new PostagemNoDTO(
                this.id,
                this.mensagem,
                this.autorId,
                this.autorNome,
                this.contadorCurtidas,
                this.dataCriacao,
                anteriorId,
                proximoId
        );
    }

    @Override
    public String toString() {
        return "PostagemNo{" +
                "id=" + id +
                ", mensagem='" + mensagem + '\'' +
                ", autorId=" + autorId +
                ", autorNome='" + autorNome + '\'' +
                ", contadorCurtidas=" + contadorCurtidas +
                ", dataCriacao=" + dataCriacao +
                ", anterior=" + anterior +
                ", proximo=" + proximo +
                '}';
    }
}
