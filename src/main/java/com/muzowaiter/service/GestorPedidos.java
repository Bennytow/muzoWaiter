package com.muzowaiter.service;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.ObservadorPedido;

import java.util.ArrayList;
import java.util.List;

public class GestorPedidos {

    private final List<Pedido> pedidos = new ArrayList<>();
    private final List<ObservadorPedido> observadores = new ArrayList<>();
    private int siguienteId = 1;

    public Pedido crearPedido() {
        Pedido pedido = new Pedido(siguienteId++);
        pedidos.add(pedido);
        notificar(pedido);
        return pedido;
    }

    public void agregarObservador(ObservadorPedido observador) {
        if (observador == null) {
            throw new IllegalArgumentException("Observador invalido");
        }
        observadores.add(observador);
    }

    public void cambiarEstado(Pedido pedido, EstadoPedido estado) {
        if (pedido == null || !pedidos.contains(pedido)) {
            throw new IllegalArgumentException("Pedido no registrado");
        }

        pedido.cambiarEstado(estado);
        notificar(pedido);
    }

    public List<Pedido> listarPedidos() {
        return List.copyOf(pedidos);
    }

    private void notificar(Pedido pedido) {
        for (ObservadorPedido observador : observadores) {
            observador.actualizar(pedido);
        }
    }
}
