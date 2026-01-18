package com.javagirls.social_media_ed.login;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Credencial é obrigatória")
    private String credencial; // Pode ser email ou nome de usuário

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    // Getters e Setters
    public String getCredencial() { return credencial; }
    public void setCredencial(String credencial) { this.credencial = credencial; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}