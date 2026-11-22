package com.muzowaiter.service;

import com.muzowaiter.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CajaRegistradora {

    private static final CajaRegistradora INSTANCIA =
            new CajaRegistradora();

    private final List<RegistroPago> pagos = new ArrayList<>();
    private final Set<Integer> pedidosCobrados = new HashSet<>();

    private LocalDate fechaCaja = LocalDate.now();
    private boolean abierta = true;

    private CajaRegistradora() {
    }

    public static CajaRegistradora getInstancia() {
        return INSTANCIA;
    }

    public synchronized void registrarPago(
            Pedido pedido, double monto, String metodo) {

        actualizarFecha();

        if (!abierta) {
            throw new IllegalStateException("La caja esta cerrada");
        }

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido obligatorio");
        }

        if (pedido.getEstado() != EstadoPedido.ENTREGADO) {
            throw new IllegalStateException(
                    "Solo se cobran pedidos entregados");
        }

        if (pedidosCobrados.contains(pedido.getId())) {
            throw new IllegalStateException("Pedido ya cobrado");
        }

        if (metodo == null || metodo.isBlank()) {
            throw new IllegalArgumentException("Metodo obligatorio");
        }

        if (!Double.isFinite(monto) || monto < 0) {
            throw new IllegalArgumentException("Monto invalido");
        }

        pagos.add(new RegistroPago(
                pedido.getId(),
                monto,
                metodo,
                LocalDateTime.now()
        ));

        pedidosCobrados.add(pedido.getId());
    }

    public synchronized double obtenerTotal() {
        actualizarFecha();

        return pagos.stream()
                .mapToDouble(RegistroPago::monto)
                .sum();
    }

    public synchronized List<RegistroPago> obtenerPagos() {
        actualizarFecha();
        return List.copyOf(pagos);
    }

    public synchronized ReporteCaja cerrarCaja() {
        actualizarFecha();

        if (!abierta) {
            throw new IllegalStateException("Caja ya cerrada");
        }

        ReporteCaja reporte = new ReporteCaja(
                fechaCaja,
                pagos,
                obtenerTotal()
        );

        abierta = false;
        return reporte;
    }

    public synchronized void abrirCaja() {
        actualizarFecha();

        if (abierta) {
            throw new IllegalStateException("Caja ya abierta");
        }

        pagos.clear();
        pedidosCobrados.clear();
        abierta = true;
    }

    public synchronized boolean estaAbierta() {
        actualizarFecha();
        return abierta;
    }

    private void actualizarFecha() {
        LocalDate hoy = LocalDate.now();

        if (!hoy.equals(fechaCaja)) {
            // No borrar ventas automaticamente.
            // Es obligatorio cerrar la caja anterior.
            if (!pagos.isEmpty()) {
                throw new IllegalStateException(
                    "Hay ventas de una fecha anterior. "
                    + "Debe realizarse el cierre antes del nuevo dia."
                );
            }

            fechaCaja = hoy;
            abierta = true;
        }
    }
}
