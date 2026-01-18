package com.javagirls.social_media_ed.grafo;

import com.javagirls.social_media_ed.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ListaAdjacenteGrafo implements Grafo {

    private final Map<Integer, Usuario> vertices;
    private final Map<Integer, Set<Integer>> adjacencias;
    private int totalArestas;

    public ListaAdjacenteGrafo() {
        this.vertices = new ConcurrentHashMap<>();
        this.adjacencias = new ConcurrentHashMap<>();
        this.totalArestas = 0;
    }

    @Override
    public void adicionarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }

        int id = usuario.getId();

        if (!vertices.containsKey(id)) {
            vertices.put(id, usuario);
            adjacencias.put(id, ConcurrentHashMap.newKeySet());
            System.out.println("Usuário " + usuario.getNomeUsuario() + " adicionado ao grafo.");
        } else {
            System.out.println("Usuário com ID " + id + " já existe no grafo.");
        }
    }

    @Override
    public Optional<Usuario> buscarUsuario(int id) {
        Usuario usuario = vertices.get(id);

        if (usuario != null) {
            return Optional.of(usuario);
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado.");
            return Optional.empty();
        }
    }

    public void adicionarConexao(int idUsuario1, int idUsuario2) {
        if (vertices.containsKey(idUsuario1) && vertices.containsKey(idUsuario2)) {
            adjacencias.get(idUsuario1).add(idUsuario2);
            adjacencias.get(idUsuario2).add(idUsuario1);
            totalArestas++;
            System.out.println("Conexão adicionada entre " + idUsuario1 + " e " + idUsuario2);
        }
    }

    public List<Usuario> getConexoesUsuario(int idUsuario) {
        if (!vertices.containsKey(idUsuario)) {
            System.out.println("Usuário com ID " + idUsuario + " não encontrado no grafo.");
            return Collections.emptyList();
        }

        Set<Integer> conexoesIds = adjacencias.get(idUsuario);

        return conexoesIds.stream()
                .map(vertices::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}