package com.javagirls.social_media_ed.amizade;

import com.javagirls.social_media_ed.postagem.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoAmizadeRepository extends JpaRepository<SolicitacaoAmizade, Integer> {

    List<SolicitacaoAmizade> findAllByIdDestinatario(Integer idDestinatario);
}
