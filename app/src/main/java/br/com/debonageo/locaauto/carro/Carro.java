package br.com.debonageo.locaauto.carro;

public class Carro {

    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método a ser chamado no spinner
    @Override
    public String toString() {
        return nome;
    }
}
