package com.exemplo.pagamentos.chain;

import com.exemplo.pagamentos.model.Item;
import com.exemplo.pagamentos.model.Pedido;
import com.exemplo.pagamentos.model.StatusPedido;

public class ValidadorEstoque extends ValidadorPedido {

    @Override
    protected String executarValidacao(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            pedido.setStatus(StatusPedido.REJEITADO);
            return "REJEITADO: Pedido sem itens";
        }

        for (Item item : pedido.getItens()) {
            if (!item.isEmEstoque()) {
                pedido.setStatus(StatusPedido.REJEITADO);
                return "REJEITADO: Item '" + item.getNome() + "' fora de estoque";
            }
        }

        return "Estoque validado com sucesso";
    }
}