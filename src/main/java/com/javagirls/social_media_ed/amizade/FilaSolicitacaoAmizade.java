package com.javagirls.social_media_ed.amizade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FilaSolicitacaoAmizade {

    private static final int TAMANHO_MAXIMO = 100;

    private SolicitacaoAmizade[] fila;
    private int tamanhoAtual;

    private static final Logger log = LoggerFactory.getLogger(FilaSolicitacaoAmizade.class);

    public FilaSolicitacaoAmizade() {
        this.fila = new SolicitacaoAmizade[TAMANHO_MAXIMO];
        this.tamanhoAtual = 0;
    }

    public SolicitacaoAmizade enfileirar(SolicitacaoDTO solicitacaoDados) {
        SolicitacaoAmizade novaSolicitacao = solicitacaoDados.toSolicitacaoAmizade();

        log.info("Solicitacao: "+novaSolicitacao);
        this.fila[this.tamanhoAtual] = novaSolicitacao;

        this.tamanhoAtual++;

        return novaSolicitacao;
    }

    public Optional<SolicitacaoAmizade> desenfileirar() {
        if (tamanhoAtual == 0) {
            return Optional.empty();
        }

        SolicitacaoAmizade primeiraSolicitacao = fila[0];

        for (int i = 0; i < tamanhoAtual - 1; i++) {
            fila[i] = fila[i + 1];
        }

        fila[tamanhoAtual - 1] = null;
        tamanhoAtual--;

        return Optional.of(primeiraSolicitacao);
    }

    public SolicitacaoAmizade[] imprimirFila() {

        log.info("Fila atualizada: "+this.tamanhoAtual);
        return this.fila;
    }
}