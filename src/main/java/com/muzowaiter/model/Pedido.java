package com.muzowaiter.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final int id;
    private final List<Producto> productos = new ArrayList<>();
    private EstadoPedido estado = EstadoPedido.RECIBIDO;

    public Pedido(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("Producto invalido");
        }
        if (estado != EstadoPedido.RECIBIDO) {
            throw new IllegalStateException("El pedido ya no se puede modificar");
        }
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return List.copyOf(productos);
    }

    public double getTotal() {
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("Estado invalido");
        }

        boolean valido = switch (estado) {
            case RECIBIDO -> nuevoEstado == EstadoPedido.EN_PREPARACION
                    || nuevoEstado == EstadoPedido.CANCELADO;
            case EN_PREPARACION -> nuevoEstado == EstadoPedido.LISTO
                    || nuevoEstado == EstadoPedido.CANCELADO;
            case LISTO -> nuevoEstado == EstadoPedido.ENTREGADO;
            case ENTREGADO, CANCELADO -> false;
        };

        if (!valido) {
            throw new IllegalStateException(
                "Transicion invalida: " + estado + " -> " + nuevoEstado
            );
        }

        this.estado = nuevoEstado;
    }
}
