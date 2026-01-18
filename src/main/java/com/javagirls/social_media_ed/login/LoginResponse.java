package com.javagirls.social_media_ed.login;

public class LoginResponse {
    private Integer id;
    private String nomeUsuario;
    private String email;
    private String token;

    public LoginResponse(Integer id, String nomeUsuario, String email, String token) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.token = token;
    }

    // Getters
    public Integer getId() { return id; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}