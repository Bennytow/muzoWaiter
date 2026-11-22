package com.muzowaiter.model;

import java.time.LocalDateTime;

public record RegistroPago(
        int pedidoId,
        double monto,
        String metodo,
        LocalDateTime fecha
) {
    public RegistroPago {
        if (pedidoId <= 0 || !Double.isFinite(monto) || monto < 0
                || metodo == null || metodo.isBlank()
                || fecha == null) {
            throw new IllegalArgumentException("Datos de pago invalidos");
        }
    }
}
