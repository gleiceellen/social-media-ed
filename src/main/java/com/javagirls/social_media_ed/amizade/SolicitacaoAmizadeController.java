package com.javagirls.social_media_ed.amizade;

import com.javagirls.social_media_ed.commons.UsuarioLogadoComponent;
import com.javagirls.social_media_ed.grafo.ListaAdjacenteGrafo;
import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes-amizade")
public class SolicitacaoAmizadeController {

    private final FilaSolicitacaoAmizade fila;
    private final ListaAdjacenteGrafo grafo;
    private final UsuarioLogadoComponent usuarioLogadoComponent;

    public SolicitacaoAmizadeController(FilaSolicitacaoAmizade fila, ListaAdjacenteGrafo grafo, UsuarioLogadoComponent usuarioLogadoComponent) {
        this.fila = fila;
        this.grafo = grafo;
        this.usuarioLogadoComponent = usuarioLogadoComponent;
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
        Integer usuarioId = usuarioLogadoComponent.getUsuarioLogado().getId();
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