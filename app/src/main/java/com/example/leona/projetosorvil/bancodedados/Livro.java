package com.example.leona.projetosorvil.bancodedados;

import java.io.Serializable;

public class Livro implements Serializable{
    private String Titulo;
    private String Autor;
    private String Ano;
    private String Id;

    public Livro(){
        Titulo = Autor = Ano = Id = null;
    }

    public Livro(String titulo,String autor, String ano, String id){
        this.Titulo = titulo;
        this.Autor = autor;
        this.Ano = ano;
        this.Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }
}
