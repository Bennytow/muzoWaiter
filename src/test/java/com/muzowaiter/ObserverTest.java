package com.muzowaiter;

import com.muzowaiter.model.*;
import com.muzowaiter.service.GestorPedidos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    @Test
    void notificarCambiosDeEstado() {
        GestorPedidos gestor = new GestorPedidos();
        List<EstadoPedido> avisos = new ArrayList<>();

        gestor.agregarObservador(p -> avisos.add(p.getEstado()));

        Pedido pedido = gestor.crearPedido();
        pedido.agregarProducto(new ProductoBase("Cafe", 4000));

        gestor.cambiarEstado(pedido, EstadoPedido.EN_PREPARACION);
        gestor.cambiarEstado(pedido, EstadoPedido.LISTO);
        gestor.cambiarEstado(pedido, EstadoPedido.ENTREGADO);

        assertEquals(List.of(
                EstadoPedido.RECIBIDO,
                EstadoPedido.EN_PREPARACION,
                EstadoPedido.LISTO,
                EstadoPedido.ENTREGADO
        ), avisos);
    }

    @Test
    void notificarTresObservadores() {
        GestorPedidos gestor = new GestorPedidos();
        List<String> avisos = new ArrayList<>();

        gestor.agregarObservador(p -> avisos.add("Cocina"));
        gestor.agregarObservador(p -> avisos.add("Caja"));
        gestor.agregarObservador(p -> avisos.add("Mesero"));

        gestor.crearPedido();

        assertEquals(3, avisos.size());
    }

    @Test
    void rechazarTransicionInvalida() {
        GestorPedidos gestor = new GestorPedidos();
        Pedido pedido = gestor.crearPedido();

        assertThrows(IllegalStateException.class,
                () -> gestor.cambiarEstado(
                        pedido, EstadoPedido.ENTREGADO));
    }
}
