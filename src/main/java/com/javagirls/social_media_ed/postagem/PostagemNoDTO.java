package com.javagirls.social_media_ed.postagem;

import java.time.LocalDateTime;

public record PostagemNoDTO(
        Integer id,
        String mensagem,
        Integer autorId,
        String autorNome,
        Integer contadorCurtidas,
        LocalDateTime dataCriacao,
        Integer idAnterior,
        Integer idProximo
) {
    // Construtor adicional a partir de PostagemNo
    public PostagemNoDTO(PostagemNo postagemNo) {
        this(
                postagemNo.getId(),
                postagemNo.getMensagem(),
                postagemNo.getAutorId(),
                postagemNo.getAutorNome(),
                postagemNo.getContadorCurtidas(),
                postagemNo.getDataCriacao(),
                postagemNo.getAnterior().getId(),
                postagemNo.getProximo().getId()
        );
    }
}