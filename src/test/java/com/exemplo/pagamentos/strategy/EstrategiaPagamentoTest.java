package com.exemplo.pagamentos.strategy;

import com.exemplo.pagamentos.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaPagamentoTest {

    @Test
    void pixDeveTerTaxaZero() {
        Pedido pedido = criarPedido(TipoPagamento.PIX);
        EstrategiaPagamento estrategia = new PagamentoPix();

        assertEquals(BigDecimal.ZERO, estrategia.calcularTaxa(pedido));
        assertEquals("PIX", estrategia.getNome());
        assertTrue(estrategia.pagar(pedido).contains("PIX"));
    }

    @Test
    void cartaoCreditoDeveTerTaxa3e5Porcento() {
        Pedido pedido = criarPedido(TipoPagamento.CARTAO_CREDITO);
        EstrategiaPagamento estrategia = new PagamentoCartaoCredito();

        assertEquals(new BigDecimal("3.50"), estrategia.calcularTaxa(pedido));
        assertEquals("Cartão de Crédito", estrategia.getNome());
    }

    @Test
    void boletoDeveTerTaxaFixa() {
        Pedido pedido = criarPedido(TipoPagamento.BOLETO);
        EstrategiaPagamento estrategia = new PagamentoBoleto();

        assertEquals(new BigDecimal("3.50"), estrategia.calcularTaxa(pedido));
        assertEquals("Boleto", estrategia.getNome());
    }

    private Pedido criarPedido(TipoPagamento tipo) {
        Pedido p = new Pedido();
        p.setId("PED-001");
        p.setCliente(new Cliente("Maria", "maria@email.com", "123", true));
        p.setTipoPagamento(tipo);
        p.setItens(List.of(new Item("Produto", 2, BigDecimal.valueOf(50), true)));
        p.calcularTotal();
        return p;
    }
}