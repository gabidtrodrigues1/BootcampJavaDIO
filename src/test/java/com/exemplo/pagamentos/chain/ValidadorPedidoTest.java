package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorPedidoTest {

    private ValidadorPedido cadeia;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cadeia = new ValidadorDadosCliente();
        cadeia.encadear(new ValidadorEstoque())
              .encadear(new ValidadorPagamento())
              .encadear(new ValidadorLimite());

        pedido = new Pedido();
        pedido.setId("PED-001");
        pedido.setCliente(new Cliente("João", "joao@email.com", "12345678901", true));
        pedido.setTipoPagamento(TipoPagamento.PIX);
        pedido.setItens(List.of(
                new Item("Mouse", 2, BigDecimal.valueOf(50), true)
        ));
        pedido.calcularTotal();
    }

    @Test
    void deveValidarPedidoCompletoComSucesso() {
        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.PENDENTE, pedido.getStatus());
        assertTrue(resultado.contains("Dados do cliente validados"));
        assertTrue(resultado.contains("Estoque validado"));
        assertTrue(resultado.contains("validado com sucesso"));
    }

    @Test
    void deveRejeitarPedidoSemCliente() {
        pedido.setCliente(null);

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("REJEITADO: Cliente não informado"));
    }

    @Test
    void deveRejeitarPedidoClienteInativo() {
        pedido.getCliente().setAtivo(false);

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("REJEITADO: Cliente inativo"));
    }

    @Test
    void deveRejeitarPedidoSemItens() {
        pedido.setItens(List.of());

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("REJEITADO: Pedido sem itens"));
    }

    @Test
    void deveRejeitarItemForaDeEstoque() {
        pedido.setItens(List.of(
                new Item("Teclado", 1, BigDecimal.valueOf(200), false)
        ));

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("fora de estoque"));
    }

    @Test
    void deveRejeitarPedidoSemTipoPagamento() {
        pedido.setTipoPagamento(null);

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("REJEITADO: Tipo de pagamento não informado"));
    }

    @Test
    void deveRejeitarPedidoAcimaDoLimite() {
        pedido.setItens(List.of(
                new Item("Notebook", 1, BigDecimal.valueOf(6000), true)
        ));
        pedido.calcularTotal();

        String resultado = cadeia.validar(pedido);

        assertEquals(StatusPedido.REJEITADO, pedido.getStatus());
        assertTrue(resultado.contains("REJEITADO: Pedido excede o limite"));
    }
}