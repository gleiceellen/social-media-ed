package com.javagirls.social_media_ed.login;

import com.javagirls.social_media_ed.usuario.Usuario;
import com.javagirls.social_media_ed.usuario.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${jwt.secret:wTZb5x8z/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w!z%C*F-JaNdR}")  // Leia do application.yml
    private String jwtSecret = "wTZb5x8zADKbPeShVmYq3s6v9yBEHMcQfTjWnZr4u7wzCFJaNdR";

    @Value("${jwt.expiration:86400000}")  // 24 horas default
    private long jwtExpiration = 86400000;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SecretKey secretKey;

    public AuthService() {
        // Gera uma chave segura baseada no secret
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Usuario registrar(RegistroDTO registroDTO) {
        // Verifica se usuário já existe
        if (usuarioRepository.existsByNomeUsuario(registroDTO.getNomeUsuario())) {
            throw new RuntimeException("Nome de usuário já está em uso");
        }

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(registroDTO.getSenha());

        // Cria novo usuário
        Usuario usuario = new Usuario(
                registroDTO.getNomeUsuario(),
                registroDTO.getEmail(),
                senhaCriptografada
        );

        return usuarioRepository.save(usuario);
    }

    public Optional<LoginResponse> login(LoginDTO loginDTO) {
        // Tenta encontrar por nome de usuário primeiro
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNomeUsuario(loginDTO.getCredencial());

        // Se não encontrar, tenta por email
        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByEmail(loginDTO.getCredencial());
        }

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verifica a senha
            if (passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                // Gera token JWT
                String token = gerarToken(usuario);

                return Optional.of(new LoginResponse(
                        usuario.getId(),
                        usuario.getNomeUsuario(),
                        usuario.getEmail(),
                        token
                ));
            }
        }

        return Optional.empty();
    }

    private String gerarToken(Usuario usuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(usuario.getNomeUsuario())
                .claim("id", usuario.getId())
                .claim("email", usuario.getEmail())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Optional<Usuario> validarToken(String token) {
        try {
            String nomeUsuario = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            return usuarioRepository.findByNomeUsuario(nomeUsuario);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}