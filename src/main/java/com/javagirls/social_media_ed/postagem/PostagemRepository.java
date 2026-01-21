package com.javagirls.social_media_ed.postagem;

import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

    @Query("SELECT p.curtidas FROM Postagem p WHERE p.id = :postId")
    Set<Usuario> findCurtidasByPostId(@Param("postId") Integer postId);

    List<Postagem> findAllByOrderByDataCriacaoDesc();
}