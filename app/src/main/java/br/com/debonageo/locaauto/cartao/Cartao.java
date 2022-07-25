package br.com.debonageo.locaauto.cartao;

public class Cartao {

    private int id;
    private String numero;
    private String nome;
    private String validade;
    private String cvv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = "01/" + validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    // Método a ser chamado no spinner
    @Override
    public String toString() {
        return numero;
    }
}
