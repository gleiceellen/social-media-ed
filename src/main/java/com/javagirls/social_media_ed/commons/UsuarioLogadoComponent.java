package com.javagirls.social_media_ed.commons;

import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogadoComponent {
    public Usuario getUsuarioLogado() {
        // Obter o usuário logado do SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || principal.equals("anonymousUser")) {
            throw new RuntimeException("Usuário não autenticado");
        }

        // O filtro JwtAuthenticationFilter coloca o objeto Usuario como principal
        Usuario usuarioLogado = (Usuario) principal;
        return usuarioLogado;
    }
}
