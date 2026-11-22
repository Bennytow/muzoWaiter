package com.muzowaiter.service;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.EstrategiaPromocion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public final class CajaRegistradora {

    private static final CajaRegistradora INSTANCIA =
            new CajaRegistradora();

    private final List<RegistroPago> pagos = new ArrayList<>();
    private final Set<Integer> cobrados = new HashSet<>();

    private LocalDate fechaCaja = LocalDate.now();
    private boolean abierta = true;

    private CajaRegistradora() {}

    public static CajaRegistradora getInstancia() {
        return INSTANCIA;
    }

    public synchronized void registrarPago(
            Pedido pedido, double monto, String metodo) {
        registrarPago(pedido, monto, metodo, null);
    }

    public synchronized void registrarPago(
            Pedido pedido, double monto, String metodo,
            EstrategiaPromocion promocion) {

        LocalDateTime ahora = LocalDateTime.now();

        if (!abierta || !fechaCaja.equals(ahora.toLocalDate())) {
            throw new IllegalStateException(
                    "Caja cerrada o cierre anterior pendiente");
        }

        if (pedido == null ||
                pedido.getEstado() != EstadoPedido.ENTREGADO) {
            throw new IllegalStateException(
                    "Solo se cobran pedidos entregados");
        }

        if (cobrados.contains(pedido.getId())) {
            throw new IllegalStateException("Pedido ya cobrado");
        }

        if (metodo == null || metodo.isBlank()) {
            throw new IllegalArgumentException("Metodo invalido");
        }

        double esperado = pedido.getTotal();

        if (promocion != null) {
            esperado = promocion.aplicarDescuento(esperado, ahora);
        }

        if (!Double.isFinite(esperado) || esperado < 0
                || !Double.isFinite(monto)
                || Math.abs(monto - esperado) > 0.009) {
            throw new IllegalArgumentException(
                    "Monto incorrecto. Total esperado: $" + esperado);
        }

        pagos.add(new RegistroPago(
                pedido.getId(), monto, metodo, ahora));

        cobrados.add(pedido.getId());
    }

    public synchronized double obtenerTotal() {
        return pagos.stream()
                .mapToDouble(RegistroPago::monto)
                .sum();
    }

    public synchronized List<RegistroPago> obtenerPagos() {
        return List.copyOf(pagos);
    }

    public synchronized ReporteCaja cerrarCaja() {
        if (!abierta) {
            throw new IllegalStateException("Caja ya cerrada");
        }

        ReporteCaja reporte = new ReporteCaja(
                fechaCaja, pagos, obtenerTotal());

        abierta = false;
        return reporte;
    }

    public synchronized void abrirCaja() {
        if (abierta) {
            throw new IllegalStateException("Caja ya abierta");
        }

        if (!LocalDate.now().isAfter(fechaCaja)) {
            throw new IllegalStateException(
                    "Solo puede abrir una nueva caja al dia siguiente");
        }

        pagos.clear();
        cobrados.clear();
        fechaCaja = LocalDate.now();
        abierta = true;
    }

    public synchronized boolean estaAbierta() {
        return abierta && fechaCaja.equals(LocalDate.now());
    }
}
