package com.muzowaiter.service;

import com.muzowaiter.patterns.EstrategiaPromocion;
import java.time.LocalDateTime;

public class ServicioPromociones {

    private EstrategiaPromocion estrategia;

    public void establecerEstrategia(EstrategiaPromocion estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("Estrategia obligatoria");
        }
        this.estrategia = estrategia;
    }

    public double calcularTotal(double total, LocalDateTime fecha) {
        if (total < 0 || fecha == null) {
            throw new IllegalArgumentException("Datos invalidos");
        }
        if (estrategia == null) {
            return total;
        }
        return estrategia.aplicarDescuento(total, fecha);
    }
}
