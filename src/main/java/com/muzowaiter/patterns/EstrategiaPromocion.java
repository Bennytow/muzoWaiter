package com.muzowaiter.patterns;

import java.time.LocalDateTime;

public interface EstrategiaPromocion {
    double aplicarDescuento(double total, LocalDateTime fecha);
}
