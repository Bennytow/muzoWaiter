package com.muzowaiter.service;

import com.muzowaiter.model.Pedido;
import com.muzowaiter.patterns.ObservadorPedido;

public class NotificadorCaja implements ObservadorPedido {

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println(
            "[Caja] Pedido #" + pedido.getId()
            + " - Estado: " + pedido.getEstado()
        );
    }
}
