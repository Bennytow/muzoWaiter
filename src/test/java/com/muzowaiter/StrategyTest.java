package com.muzowaiter;

import com.muzowaiter.patterns.*;
import com.muzowaiter.service.ServicioPromociones;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    @Test
    void descuentoPorHorario() {
        EstrategiaPromocion estrategia = new PromocionHorario();

        double total = estrategia.aplicarDescuento(
                20000, LocalDateTime.of(2026, 11, 23, 15, 0));

        assertEquals(18000, total, 0.01);
    }

    @Test
    void fueraDelHorario() {
        EstrategiaPromocion estrategia = new PromocionHorario();

        double total = estrategia.aplicarDescuento(
                20000, LocalDateTime.of(2026, 11, 23, 17, 0));

        assertEquals(20000, total, 0.01);
    }

    @Test
    void descuentoMiercoles() {
        EstrategiaPromocion estrategia = new PromocionDia();

        double total = estrategia.aplicarDescuento(
                20000, LocalDateTime.of(2026, 11, 25, 12, 0));

        assertEquals(17000, total, 0.01);
    }

    @Test
    void cambiarEstrategia() {
        ServicioPromociones servicio = new ServicioPromociones();
        LocalDateTime fecha = LocalDateTime.of(2026, 11, 25, 15, 0);

        servicio.establecerEstrategia(new PromocionHorario());
        assertEquals(18000, servicio.calcularTotal(20000, fecha), 0.01);

        servicio.establecerEstrategia(new PromocionDia());
        assertEquals(17000, servicio.calcularTotal(20000, fecha), 0.01);
    }
}
