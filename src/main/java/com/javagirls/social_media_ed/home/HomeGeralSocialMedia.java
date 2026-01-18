package com.javagirls.social_media_ed.home;

import com.javagirls.social_media_ed.feed.Feed;
import com.javagirls.social_media_ed.usuario.Usuario;
import com.javagirls.social_media_ed.usuario.UsuarioDTO;
import com.javagirls.social_media_ed.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeGeralSocialMedia {

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public Feed feed;

    @GetMapping("/home")
    public HomeGeral montaHome() {
        return new HomeGeral("Bem vinda a nossa rede social! Aqui o algoritmo é saudável!", retornaUsuarios(), feed);
    }

    public List<UsuarioDTO> retornaUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(Usuario::usuarioDTO).toList();
    }
}
