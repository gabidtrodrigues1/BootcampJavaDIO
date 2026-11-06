package com.exemplo.pagamentos.singleton;

import java.math.BigDecimal;

public class GerenciadorConfiguracao {

    private static volatile GerenciadorConfiguracao instancia;

    private BigDecimal taxaServico;
    private int prazoMaximoMinutos;
    private BigDecimal limiteMaximoPedido;

    private GerenciadorConfiguracao() {
        this.taxaServico = BigDecimal.valueOf(0.05);
        this.prazoMaximoMinutos = 30;
        this.limiteMaximoPedido = BigDecimal.valueOf(5000);
    }

    public static GerenciadorConfiguracao getInstancia() {
        if (instancia == null) {
            synchronized (GerenciadorConfiguracao.class) {
                if (instancia == null) {
                    instancia = new GerenciadorConfiguracao();
                }
            }
        }
        return instancia;
    }

    public BigDecimal getTaxaServico() {
        return taxaServico;
    }

    public void setTaxaServico(BigDecimal taxaServico) {
        this.taxaServico = taxaServico;
    }

    public int getPrazoMaximoMinutos() {
        return prazoMaximoMinutos;
    }

    public void setPrazoMaximoMinutos(int prazoMaximoMinutos) {
        this.prazoMaximoMinutos = prazoMaximoMinutos;
    }

    public BigDecimal getLimiteMaximoPedido() {
        return limiteMaximoPedido;
    }

    public void setLimiteMaximoPedido(BigDecimal limiteMaximoPedido) {
        this.limiteMaximoPedido = limiteMaximoPedido;
    }
}