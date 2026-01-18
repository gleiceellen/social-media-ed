package com.javagirls.social_media_ed.config;

import com.javagirls.social_media_ed.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SecurityContextUtil {

    public static Usuario getUsuarioLogado() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new IllegalStateException("Fora do contexto de requisição");
        }

        HttpServletRequest request = attributes.getRequest();
        return (Usuario) request.getAttribute("usuario");
    }
}