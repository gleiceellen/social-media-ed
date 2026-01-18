package com.javagirls.social_media_ed.amizade;

import com.javagirls.social_media_ed.grafo.ListaAdjacenteGrafo;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes-amizade")
public class SolicitacaoAmizadeController {

    private final FilaSolicitacaoAmizade fila;
    private final ListaAdjacenteGrafo grafo;

    public SolicitacaoAmizadeController(FilaSolicitacaoAmizade fila, ListaAdjacenteGrafo grafo) {
        this.fila = fila;
        this.grafo = grafo;
    }

    @PostMapping
    public ResponseEntity<String> criarSolicitacao(
            @RequestBody SolicitacaoDTO solicitacaoDados) {

        SolicitacaoAmizade solicitacao = fila.enfileirar(solicitacaoDados);

        return ResponseEntity.ok("Solicitação criada de "+solicitacao.getIdRemetente()+" para "+solicitacao.getIdDestinatario());
    }

    @GetMapping
    public ResponseEntity<SolicitacaoAmizade[]> listarSolicitacoes() {
        SolicitacaoAmizade[] filaRetornada = fila.imprimirFila();
        return ResponseEntity.ok(filaRetornada);
    }

    @GetMapping("/amigos")
    public ResponseEntity<?> minhasConexoes() {
        // Obter o usuário logado do SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || principal.equals("anonymousUser")) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        // O filtro JwtAuthenticationFilter coloca o objeto Usuario como principal
        Usuario usuarioLogado = (Usuario) principal;
        Integer usuarioId = usuarioLogado.getId();
        List<Usuario> conexoes = grafo.getConexoesUsuario(usuarioId);
        return ResponseEntity.ok(conexoes);
    }

    @PostMapping("/aceitar")
    public ResponseEntity<String> aceitarSolicitacao(
            @RequestBody SolicitacaoDTO solicitacaoDados) {

        grafo.adicionarConexao(solicitacaoDados.idRemetente, solicitacaoDados.idDestinatario);
        fila.desenfileirar();

        return ResponseEntity.ok("Solicitação aceita de "+solicitacaoDados.idRemetente+" para "+solicitacaoDados.idDestinatario);
    }
}