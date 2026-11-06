package com.exemplo.pagamentos.service;

import com.exemplo.pagamentos.chain.*;
import com.exemplo.pagamentos.dto.ItemRequest;
import com.exemplo.pagamentos.dto.PedidoRequest;
import com.exemplo.pagamentos.dto.PedidoResponse;
import com.exemplo.pagamentos.factory.PagamentoFactory;
import com.exemplo.pagamentos.model.*;
import com.exemplo.pagamentos.observer.EnviaEmailObserver;
import com.exemplo.pagamentos.observer.IntegraErpObserver;
import com.exemplo.pagamentos.observer.PublicadorEventos;
import com.exemplo.pagamentos.observer.RegistraLogObserver;
import com.exemplo.pagamentos.strategy.EstrategiaDesconto;
import com.exemplo.pagamentos.strategy.EstrategiaPagamento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final ValidadorPedido cadeiaValidacao;
    private final PublicadorEventos publicador;
    private final PagamentoFactory pagamentoFactory;

    public PedidoService() {
        this.publicador = new PublicadorEventos();
        this.publicador.assinar(new EnviaEmailObserver());
        this.publicador.assinar(new RegistraLogObserver());
        this.publicador.assinar(new IntegraErpObserver());

        this.pagamentoFactory = new PagamentoFactory();

        this.cadeiaValidacao = new ValidadorDadosCliente();
        this.cadeiaValidacao
                .encadear(new ValidadorEstoque())
                .encadear(new ValidadorPagamento())
                .encadear(new ValidadorLimite());
    }

    public PedidoResponse processarPedido(PedidoRequest request) {
        Pedido pedido = mapearParaPedido(request);
        pedido.calcularTotal();

        PedidoResponse response = new PedidoResponse();
        response.setPedidoId(pedido.getId());

        String resultadoValidacao = cadeiaValidacao.validar(pedido);
        response.setValidacoes(parseValidacoes(resultadoValidacao));

        if (pedido.getStatus() == StatusPedido.REJEITADO) {
            response.setStatus("REJEITADO");
            response.setValorTotal(pedido.getValorTotal());
            response.setValorDesconto(BigDecimal.ZERO);
            response.setValorFinal(pedido.getValorTotal());
            response.setFormaPagamento(request.getTipoPagamento());
            response.setNotificacoes(new ArrayList<>());
            return response;
        }

        pedido.setStatus(StatusPedido.VALIDADO);

        EstrategiaPagamento estrategiaPagamento = pagamentoFactory.obterEstrategiaPagamento(pedido);
        EstrategiaDesconto estrategiaDesconto = pagamentoFactory.obterEstrategiaDesconto(pedido);

        BigDecimal desconto = estrategiaDesconto.calcular(pedido);
        BigDecimal taxa = estrategiaPagamento.calcularTaxa(pedido);
        BigDecimal valorFinal = pedido.getValorTotal().subtract(desconto).add(taxa);

        estrategiaPagamento.pagar(pedido);
        pedido.setStatus(StatusPedido.PAGO);
        pedido.setValorTotal(valorFinal);

        List<String> notificacoes = publicador.notificarTodos(pedido);

        response.setStatus(pedido.getStatus().name());
        response.setValorTotal(pedido.getValorTotal().add(desconto).subtract(taxa));
        response.setValorDesconto(desconto);
        response.setValorFinal(valorFinal);
        response.setFormaPagamento(estrategiaPagamento.getNome());
        response.setNotificacoes(notificacoes);

        return response;
    }

    private Pedido mapearParaPedido(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID().toString().substring(0, 8));
        pedido.setTipoPagamento(TipoPagamento.valueOf(request.getTipoPagamento()));

        Cliente cliente = new Cliente(
                request.getClienteNome(),
                request.getClienteEmail(),
                request.getClienteCpf(),
                true
        );
        pedido.setCliente(cliente);

        List<Item> itens = new ArrayList<>();
        for (ItemRequest itemReq : request.getItens()) {
            Item item = new Item(
                    itemReq.getNome(),
                    itemReq.getQuantidade(),
                    itemReq.getPrecoUnitario(),
                    true
            );
            itens.add(item);
        }
        pedido.setItens(itens);

        return pedido;
    }

    private List<String> parseValidacoes(String resultado) {
        List<String> lista = new ArrayList<>();
        if (resultado == null || resultado.isEmpty()) {
            return lista;
        }
        for (String parte : resultado.split("; ")) {
            lista.add(parte.trim());
        }
        return lista;
    }
}