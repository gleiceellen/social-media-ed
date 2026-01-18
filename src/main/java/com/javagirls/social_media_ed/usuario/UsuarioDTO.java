package com.javagirls.social_media_ed.usuario;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UsuarioDTO {
    private Integer id;
    private String nomeUsuario;
    private String email;


    public UsuarioDTO() {}

    public UsuarioDTO(String nomeUsuario, String email) {
        this.nomeUsuario = nomeUsuario;
        this.email = email;
    }

    public UsuarioDTO(Integer id, String nomeUsuario, String email) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
