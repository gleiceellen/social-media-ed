package com.javagirls.social_media_ed.amizade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class SolicitacaoAmizade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idRemetente;
    private Integer idDestinatario;
    private String mensagem;

    public SolicitacaoAmizade() {}

    public SolicitacaoAmizade(Integer idRemetente, Integer idDestinatario, String mensagem) {
        this.idRemetente = idRemetente;
        this.idDestinatario = idDestinatario;
        this.mensagem = mensagem;
    }

    public Integer getId() { return id; }
    public Integer getIdRemetente() { return idRemetente; }
    public Integer getIdDestinatario() { return idDestinatario; }
    public String getMensagem() { return mensagem; }
}
