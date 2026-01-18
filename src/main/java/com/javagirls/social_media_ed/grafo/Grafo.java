package com.javagirls.social_media_ed.grafo;


import com.javagirls.social_media_ed.usuario.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define operações básicas de grafo.
 * Boa prática para ensino: separar interface da implementação.
 */
public interface Grafo {

    // Operações com vértices
    void adicionarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuario(int id);
    List<Usuario> getConexoesUsuario(int id);
}