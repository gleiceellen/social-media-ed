package com.javagirls.social_media_ed.login;

import com.javagirls.social_media_ed.grafo.ListaAdjacenteGrafo;
import com.javagirls.social_media_ed.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ListaAdjacenteGrafo grafo;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDTO registroDTO) {
        try {
            Usuario usuario = authService.registrar(registroDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuário registrado com sucesso");
            response.put("id", usuario.getId());
            response.put("nomeUsuario", usuario.getNomeUsuario());
            response.put("email", usuario.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/registrar-lote")
    public ResponseEntity<?> registrarLote(@Valid @RequestBody List<RegistroDTO> registros) {
        try {
            Map<String, Object> response = new HashMap<>();
            for(RegistroDTO registro : registros) {
                Usuario usuario = authService.registrar(registro);

                grafo.adicionarUsuario(usuario);

                response.put("message", "Usuário registrado com sucesso");
                response.put("id", usuario.getId());
                response.put("nomeUsuario", usuario.getNomeUsuario());
                response.put("email", usuario.getEmail());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        Optional<LoginResponse> loginResponse = authService.login(loginDTO);
        if (loginResponse.isPresent()) {
            return ResponseEntity.ok(loginResponse.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciais inválidas"));
        }
    }

    @GetMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Token não fornecido"));
        }

        String token = authHeader.substring(7);

        return authService.validarToken(token)
                .map(usuario -> ResponseEntity.ok(Map.of(
                        "valid", true,
                        "usuario", Map.of(
                                "id", usuario.getId(),
                                "nomeUsuario", usuario.getNomeUsuario(),
                                "email", usuario.getEmail()
                        )
                )))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "error", "Token inválido ou expirado")));
    }
}
