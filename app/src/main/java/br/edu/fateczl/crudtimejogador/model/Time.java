/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.model;

public class Time {
    private int codigo;
    private String nome;
    private String cidade;
    private String dataFundacao;

    public String getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(String dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " - " + cidade + " - " + dataFundacao;
    }
}
