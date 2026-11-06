package com.exemplo.pagamentos.factory;

import com.exemplo.pagamentos.model.*;
import com.exemplo.pagamentos.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoFactoryTest {

    private PagamentoFactory factory;

    @BeforeEach
    void setUp() {
        factory = new PagamentoFactory();
    }

    @Test
    void deveCriarEstrategiaPix() {
        Pedido pedido = criarPedido(TipoPagamento.PIX);

        EstrategiaPagamento estrategia = factory.obterEstrategiaPagamento(pedido);

        assertTrue(estrategia instanceof PagamentoPix);
    }

    @Test
    void deveCriarEstrategiaCartaoCredito() {
        Pedido pedido = criarPedido(TipoPagamento.CARTAO_CREDITO);

        EstrategiaPagamento estrategia = factory.obterEstrategiaPagamento(pedido);

        assertTrue(estrategia instanceof PagamentoCartaoCredito);
    }

    @Test
    void deveCriarEstrategiaBoleto() {
        Pedido pedido = criarPedido(TipoPagamento.BOLETO);

        EstrategiaPagamento estrategia = factory.obterEstrategiaPagamento(pedido);

        assertTrue(estrategia instanceof PagamentoBoleto);
    }

    @Test
    void deveCriarDescontoPix() {
        Pedido pedido = criarPedido(TipoPagamento.PIX);

        EstrategiaDesconto desconto = factory.obterEstrategiaDesconto(pedido);

        assertTrue(desconto instanceof DescontoPix);
        assertEquals(new BigDecimal("5.00"), desconto.calcular(pedido));
    }

    @Test
    void deveCriarSemDescontoParaCartao() {
        Pedido pedido = criarPedido(TipoPagamento.CARTAO_CREDITO);

        EstrategiaDesconto desconto = factory.obterEstrategiaDesconto(pedido);

        assertTrue(desconto instanceof SemDesconto);
        assertEquals(BigDecimal.ZERO, desconto.calcular(pedido));
    }

    private Pedido criarPedido(TipoPagamento tipo) {
        Pedido p = new Pedido();
        p.setId("PED-001");
        p.setCliente(new Cliente("Ana", "ana@email.com", "123", true));
        p.setTipoPagamento(tipo);
        p.setItens(List.of(new Item("Produto", 2, BigDecimal.valueOf(50), true)));
        p.calcularTotal();
        return p;
    }
}