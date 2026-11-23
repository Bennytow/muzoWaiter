package com.muzowaiter;

import com.muzowaiter.model.*;
import com.muzowaiter.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    @Test
    void existeUnaSolaInstancia() {
        assertSame(
                CajaRegistradora.getInstancia(),
                CajaRegistradora.getInstancia());
    }

    @Test
    void validarCobro() {
        CajaRegistradora caja = CajaRegistradora.getInstancia();

        GestorPedidos gestor = new GestorPedidos();
        Pedido pedido = gestor.crearPedido();

        pedido.agregarProducto(new ProductoBase("Pizza", 18000));

        assertThrows(IllegalStateException.class,
                () -> caja.registrarPago(pedido, 18000, "EFECTIVO"));

        gestor.cambiarEstado(pedido, EstadoPedido.EN_PREPARACION);
        gestor.cambiarEstado(pedido, EstadoPedido.LISTO);
        gestor.cambiarEstado(pedido, EstadoPedido.ENTREGADO);

        assertThrows(IllegalArgumentException.class,
                () -> caja.registrarPago(pedido, 1000, "EFECTIVO"));

        caja.registrarPago(pedido, 18000, "EFECTIVO");

        assertEquals(18000, caja.obtenerTotal(), 0.01);

        assertThrows(IllegalStateException.class,
                () -> caja.registrarPago(pedido, 18000, "EFECTIVO"));
    }
}
