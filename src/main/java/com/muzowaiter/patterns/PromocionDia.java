package com.muzowaiter.patterns;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class PromocionDia implements EstrategiaPromocion {

    @Override
    public double aplicarDescuento(double total, LocalDateTime fecha) {

        if (fecha.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            return total * 0.85;
        }

        return total;
    }
}
