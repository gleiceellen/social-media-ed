package com.javagirls.social_media_ed.feed;

import com.javagirls.social_media_ed.postagem.*;
import com.javagirls.social_media_ed.usuario.Usuario;
import com.javagirls.social_media_ed.usuario.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private final Feed feed;
    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;

    public FeedController(Feed feed, PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.feed = feed;
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/postagem")
    public ResponseEntity<PostagemNoDTO> adicionarPostagem(@RequestBody RequisicaoPostagem request) {
        PostagemNo novaPostagem = feed.adicionarNoInicio(request.mensagem());
        return ResponseEntity.ok(novaPostagem.toDTO());
    }

    @PostMapping("/postagem/curtidas")
    public ResponseEntity<Map<String, Object>> curtirPostagem(@RequestBody RequisicaoCurtidaPostagem requisicao) {
        Map<String, Object> response = new HashMap<>();

        try {
            Usuario usuario = usuarioRepository.findById(requisicao.idUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Postagem postagem = postagemRepository.findById(requisicao.idPostagem())
                    .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

            if (postagem.getCurtidas().contains(usuario)) {
                response.put("erro", "Usuário já curtiu esta postagem");
                response.put("postagemId", requisicao.idPostagem());
                response.put("usuarioId", requisicao.idUsuario());
                return ResponseEntity.badRequest().body(response);
            }

            postagem.adicionarCurtida(usuario);
            Postagem postagemAtualizada = postagemRepository.save(postagem);

            feed.atualizarCurtidaNoFeed(postagemAtualizada.getId(), postagemAtualizada.getContadorCurtidas());

            Set<Usuario> curtidasExistentes = postagemRepository.findCurtidasByPostId(requisicao.idPostagem());
            Set<Integer> curtidasRecuperadas = curtidasExistentes.stream()
                    .map(Usuario::getId)
                    .collect(Collectors.toSet());

            Map<Integer, Set<Integer>> curtidasPorUsuario = new HashMap<>();
            curtidasPorUsuario.put(requisicao.idPostagem(), curtidasRecuperadas);

            response.put("mensagem", "Postagem curtida com sucesso");
            response.put("curtidasPorUsuario", curtidasPorUsuario);
            response.put("postagemId", requisicao.idPostagem());
            response.put("totalCurtidas", postagemAtualizada.getContadorCurtidas());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("erro", e.getMessage());
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            response.put("erro", "Erro ao curtir postagem: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<FeedDTO> getFeed() {
        return ResponseEntity.ok(feed.toDTO());
    }

    @PostMapping("/carregar")
    public ResponseEntity<String> carregarDoBanco() {
        feed.carregarDoBanco();
        return ResponseEntity.ok("Feed carregado do banco de dados");
    }
}