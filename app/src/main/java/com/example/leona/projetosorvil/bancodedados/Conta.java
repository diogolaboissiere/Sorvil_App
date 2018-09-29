package com.example.leona.projetosorvil.bancodedados;


public class Conta {
    private String id;
    private String nome_usuario;
    private String email;

    public Conta(){
        id = null;
        nome_usuario = null;
        email = null;
    }

    public Conta(String id, String nome_usuario, String email){
        this.id = id;
        this.nome_usuario = nome_usuario;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
