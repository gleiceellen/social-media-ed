package com.javagirls.social_media_ed.postagem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javagirls.social_media_ed.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "posts")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 1000)
    private String mensagem;

    @Lob
    @Column(name = "image_data")
    private byte[] imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Usuario autor;

    // Mapeamento ManyToMany para curtidas
    @ManyToMany
    @JoinTable(
            name = "post_curtidas",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> curtidas = new HashSet<>();

    @Column(name = "like_count")
    private Integer contadorCurtidas = 0;

    @ElementCollection
    @CollectionTable(name = "post_comments", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "comment")
    private List<String> comentarios = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @JsonIgnore
    @Transient
    private Postagem anterior;

    @JsonIgnore
    @Transient
    private Postagem proximo;

    public Postagem() {
        this.dataCriacao = LocalDateTime.now();
        this.curtidas = new HashSet<>();
        this.contadorCurtidas = 0;
    }

    public Postagem(String mensagem, Usuario autor) {
        this();
        this.mensagem = mensagem;
        this.autor = autor;
    }

    // Método para adicionar curtida
    public boolean adicionarCurtida(Usuario usuario) {
        if (curtidas.add(usuario)) {
            contadorCurtidas = curtidas.size();
            return true;
        }
        return false;
    }

    // Método para remover curtida
    public boolean removerCurtida(Usuario usuario) {
        if (curtidas.remove(usuario)) {
            contadorCurtidas = curtidas.size();
            return true;
        }
        return false;
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
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode ser vazia");
        }
        this.mensagem = mensagem.trim();
    }

    public byte[] getImagem() {
        return imagem != null ? imagem.clone() : null;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem != null ? imagem.clone() : null;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo");
    }

    public Set<Usuario> getCurtidas() {
        return Collections.unmodifiableSet(curtidas);
    }

    public void setCurtidas(Set<Usuario> curtidas) {
        this.curtidas = curtidas != null ? new HashSet<>(curtidas) : new HashSet<>();
        this.contadorCurtidas = this.curtidas.size();
    }

    public Integer getContadorCurtidas() {
        return contadorCurtidas;
    }

    public List<String> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios != null ? new ArrayList<>(comentarios) : new ArrayList<>();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    protected void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Postagem getAnterior() {
        return anterior;
    }

    public void setAnterior(Postagem anterior) {
        this.anterior = anterior;
    }

    public Postagem getProximo() {
        return proximo;
    }

    public void setProximo(Postagem proximo) {
        this.proximo = proximo;
    }

    @Override
    public String toString() {
        return "Postagem{" +
                "id=" + id +
                ", mensagem='" + (mensagem != null ? mensagem.substring(0, Math.min(50, mensagem.length())) : "") + "..." +
                ", autor=" + (autor != null ? autor.getId() : null) +
                ", curtidas=" + curtidas.size() +
                ", contadorCurtidas=" + contadorCurtidas +
                ", comentarios=" + comentarios.size() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postagem postagem = (Postagem) o;
        return Objects.equals(id, postagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}