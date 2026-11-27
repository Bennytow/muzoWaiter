package com.muzowaiter.patterns;

import java.time.LocalDateTime;

public class PromocionEspecial implements EstrategiaPromocion {

    @Override
    public double aplicarDescuento(
            double total,
            LocalDateTime fecha) {

        return total * 0.80;
    }
}
