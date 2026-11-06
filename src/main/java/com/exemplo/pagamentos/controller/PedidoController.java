package com.exemplo.pagamentos.controller;

import com.exemplo.pagamentos.dto.PedidoRequest;
import com.exemplo.pagamentos.dto.PedidoResponse;
import com.exemplo.pagamentos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.processarPedido(request);
        return ResponseEntity.ok(response);
    }
}