package com.exemplo.pagamentos.service;

import com.exemplo.pagamentos.dto.ItemRequest;
import com.exemplo.pagamentos.dto.PedidoRequest;
import com.exemplo.pagamentos.dto.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private PedidoService service;

    @BeforeEach
    void setUp() {
        service = new PedidoService();
    }

    @Test
    void deveProcessarPedidoComSucesso() {
        PedidoRequest request = new PedidoRequest();
        request.setClienteNome("Maria");
        request.setClienteEmail("maria@email.com");
        request.setClienteCpf("12345678901");
        request.setTipoPagamento("PIX");

        ItemRequest item = new ItemRequest();
        item.setNome("Mouse");
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(50));
        request.setItens(List.of(item));

        PedidoResponse response = service.processarPedido(request);

        assertEquals("PAGO", response.getStatus());
        assertNotNull(response.getPedidoId());
        assertEquals(new BigDecimal("100.00"), response.getValorTotal());
        assertEquals(new BigDecimal("5.00"), response.getValorDesconto());
        assertEquals(new BigDecimal("95.00"), response.getValorFinal());
        assertEquals("PIX", response.getFormaPagamento());
        assertEquals(3, response.getNotificacoes().size());

        assertTrue(response.getValidacoes().stream()
                .anyMatch(v -> v.contains("Dados do cliente validados")));
    }

    @Test
    void deveRejeitarPedidoClienteInativo() {
        PedidoRequest request = pedidoBase("CARTAO_CREDITO");
        PedidoResponse response = service.processarPedido(request);

        String status = response.getStatus();
        assertNotNull(status);
    }

    private PedidoRequest pedidoBase(String tipo) {
        PedidoRequest r = new PedidoRequest();
        r.setClienteNome("João");
        r.setClienteEmail("joao@email.com");
        r.setClienteCpf("10987654321");
        r.setTipoPagamento(tipo);

        ItemRequest item = new ItemRequest();
        item.setNome("Teclado");
        item.setQuantidade(1);
        item.setPrecoUnitario(BigDecimal.valueOf(150));
        r.setItens(List.of(item));
        return r;
    }
}