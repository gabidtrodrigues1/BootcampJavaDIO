package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublicadorEventosTest {

    private PublicadorEventos publicador;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        publicador = new PublicadorEventos();
        pedido = new Pedido();
        pedido.setId("PED-001");
        pedido.setCliente(new Cliente("Carlos", "carlos@email.com", "123", true));
        pedido.setTipoPagamento(TipoPagamento.PIX);
        pedido.setValorTotal(BigDecimal.valueOf(100));
        pedido.setStatus(StatusPedido.PAGO);
    }

    @Test
    void deveNotificarTodosObservadores() {
        publicador.assinar(new EnviaEmailObserver());
        publicador.assinar(new RegistraLogObserver());
        publicador.assinar(new IntegraErpObserver());

        List<String> resultados = publicador.notificarTodos(pedido);

        assertEquals(3, resultados.size());
        assertTrue(resultados.get(0).contains("E-mail enviado"));
        assertTrue(resultados.get(1).contains("LOG"));
        assertTrue(resultados.get(2).contains("ERP"));
    }

    @Test
    void deveRemoverObservador() {
        ObservadorPagamento email = new EnviaEmailObserver();
        ObservadorPagamento log = new RegistraLogObserver();

        publicador.assinar(email);
        publicador.assinar(log);
        publicador.remover(email);

        List<String> resultados = publicador.notificarTodos(pedido);

        assertEquals(1, resultados.size());
        assertTrue(resultados.get(0).contains("LOG"));
    }

    @Test
    void semObservadoresDeveRetornarVazio() {
        List<String> resultados = publicador.notificarTodos(pedido);

        assertTrue(resultados.isEmpty());
    }
}