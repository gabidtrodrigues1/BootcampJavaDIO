package com.exemplo.pagamentos.observer;

import com.exemplo.pagamentos.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PublicadorEventos {

    private final List<ObservadorPagamento> observadores = new ArrayList<>();

    public void assinar(ObservadorPagamento observador) {
        observadores.add(observador);
    }

    public void remover(ObservadorPagamento observador) {
        observadores.remove(observador);
    }

    public List<String> notificarTodos(Pedido pedido) {
        List<String> resultados = new ArrayList<>();
        for (ObservadorPagamento obs : observadores) {
            resultados.add(obs.notificar(pedido));
        }
        return resultados;
    }
}