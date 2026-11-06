package com.exemplo.pagamentos.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorConfiguracaoTest {

    @Test
    void deveRetornarMesmaInstancia() {
        GerenciadorConfiguracao c1 = GerenciadorConfiguracao.getInstancia();
        GerenciadorConfiguracao c2 = GerenciadorConfiguracao.getInstancia();

        assertSame(c1, c2);
    }

    @Test
    void deveTerValoresPadrao() {
        GerenciadorConfiguracao config = GerenciadorConfiguracao.getInstancia();

        assertEquals(0, config.getTaxaServico().compareTo(new java.math.BigDecimal("0.05")));
        assertEquals(30, config.getPrazoMaximoMinutos());
        assertEquals(0, config.getLimiteMaximoPedido().compareTo(new java.math.BigDecimal("5000")));
    }
}