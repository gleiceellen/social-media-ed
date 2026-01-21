package com.javagirls.social_media_ed.feed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javagirls.social_media_ed.commons.UsuarioLogadoComponent;
import com.javagirls.social_media_ed.postagem.Postagem;
import com.javagirls.social_media_ed.postagem.PostagemNo;
import com.javagirls.social_media_ed.postagem.PostagemNoDTO;
import com.javagirls.social_media_ed.postagem.PostagemRepository;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Feed {
    private final UsuarioLogadoComponent usuarioLogadoComponent;
    private final PostagemRepository postagemRepository;
    private PostagemNo cabeca;
    private int tamanho;

    public Feed(UsuarioLogadoComponent usuarioLogadoComponent, PostagemRepository postagemRepository) {
        this.cabeca = null;
        this.tamanho = 0;
        this.usuarioLogadoComponent = usuarioLogadoComponent;
        this.postagemRepository = postagemRepository;
    }

    public PostagemNo adicionarNoInicio(String mensagem) {
        Usuario autor = usuarioLogadoComponent.getUsuarioLogado();

        Postagem novaPostagem = new Postagem(mensagem, autor);
        Postagem postagemSalva = postagemRepository.save(novaPostagem);

        PostagemNo novoNo = new PostagemNo(postagemSalva);

        if (cabeca != null) {
            novoNo.setProximo(cabeca);
            cabeca.setAnterior(novoNo);
        }

        cabeca = novoNo;
        tamanho++;

        return novoNo;
    }

    @JsonIgnore
    public List<PostagemNo> getTodasPostagens() {
        List<PostagemNo> postagens = new ArrayList<>();
        PostagemNo atual = cabeca;
        while (atual != null) {
            postagens.add(atual);
            atual = atual.getProximo();
        }
        return postagens;
    }

    public List<PostagemNoDTO> getPostagensDTO() {
        List<PostagemNo> postagens = getTodasPostagens();
        return postagens.stream()
                .map(PostagemNo::toDTO)
                .toList();
    }

    public List<PostagemNo> getPostagens() {
        return getTodasPostagens();
    }

    public int getTamanho() {
        return tamanho;
    }

    @JsonIgnore
    public PostagemNo getCabeca() {
        return cabeca;
    }

    public FeedDTO toDTO() {
        return new FeedDTO(getPostagensDTO(), getTamanho());
    }

    // Método para atualizar um nó após curtida
    public void atualizarCurtidaNoFeed(Integer postId, Integer novoContador) {
        PostagemNo atual = cabeca;
        while (atual != null) {
            if (atual.getId().equals(postId)) {
                atual.setContadorCurtidas(novoContador);
                break;
            }
            atual = atual.getProximo();
        }
    }

    // Método para carregar todas as postagens do banco no feed (útil ao iniciar)
    public void carregarDoBanco() {
        List<Postagem> todasPostagensBanco = postagemRepository.findAllByOrderByDataCriacaoDesc();

        // Limpar lista atual
        cabeca = null;
        tamanho = 0;

        // Adicionar do mais recente para o mais antigo
        for (Postagem postagem : todasPostagensBanco) {
            PostagemNo novoNo = new PostagemNo(postagem);

            if (cabeca != null) {
                novoNo.setProximo(cabeca);
                cabeca.setAnterior(novoNo);
            }

            cabeca = novoNo;
            tamanho++;
        }
    }
}