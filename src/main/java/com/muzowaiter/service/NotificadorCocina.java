package com.muzowaiter.service;

import com.muzowaiter.model.Pedido;
import com.muzowaiter.patterns.ObservadorPedido;

public class NotificadorCocina implements ObservadorPedido {

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println(
            "[Cocina] Pedido #" + pedido.getId()
            + " - Estado: " + pedido.getEstado()
        );
    }
}
