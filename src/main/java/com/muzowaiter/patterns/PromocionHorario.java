package com.muzowaiter.patterns;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PromocionHorario implements EstrategiaPromocion {

    @Override
    public double aplicarDescuento(double total, LocalDateTime fecha) {
        LocalTime hora = fecha.toLocalTime();

        if (!hora.isBefore(LocalTime.of(14, 0))
                && hora.isBefore(LocalTime.of(17, 0))) {
            return total * 0.90;
        }

        return total;
    }
}
