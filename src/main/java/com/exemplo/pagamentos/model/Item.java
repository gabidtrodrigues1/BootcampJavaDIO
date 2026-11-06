package com.exemplo.pagamentos.model;

import java.math.BigDecimal;

public class Item {

    private String nome;
    private int quantidade;
    private BigDecimal precoUnitario;
    private boolean emEstoque;

    public Item() {
    }

    public Item(String nome, int quantidade, BigDecimal precoUnitario, boolean emEstoque) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.emEstoque = emEstoque;
    }

    public BigDecimal getTotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public boolean isEmEstoque() {
        return emEstoque;
    }

    public void setEmEstoque(boolean emEstoque) {
        this.emEstoque = emEstoque;
    }
}