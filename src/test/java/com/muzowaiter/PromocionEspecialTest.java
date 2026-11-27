package com.muzowaiter;

import com.muzowaiter.patterns.PromocionEspecial;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromocionEspecialTest {

    @Test
    void aplicaDescuentoDelVeintePorCiento() {
        PromocionEspecial promocion = new PromocionEspecial();

        double total = promocion.aplicarDescuento(
                22000,
                LocalDateTime.of(2026, 11, 26, 20, 0));

        assertEquals(17600, total, 0.001);
    }

    @Test
    void funcionaEnCualquierHorario() {
        PromocionEspecial promocion = new PromocionEspecial();

        assertEquals(
                8000,
                promocion.aplicarDescuento(
                        10000,
                        LocalDateTime.of(2026, 11, 30, 8, 0)),
                0.001);
    }
}
