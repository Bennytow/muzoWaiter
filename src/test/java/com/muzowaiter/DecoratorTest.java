package com.muzowaiter;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.Adicion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    @Test
    void agregarUnaAdicion() {
        Producto producto = new ProductoBase("Hamburguesa", 15000);
        Producto decorado = new Adicion(producto, "Queso", 3000);

        assertEquals(18000, decorado.getPrecio(), 0.01);
        assertEquals("Hamburguesa + Queso", decorado.getDescripcion());
    }

    @Test
    void agregarMultiplesAdiciones() {
        Producto producto = new ProductoBase("Hamburguesa", 15000);

        producto = new Adicion(producto, "Queso", 3000);
        producto = new Adicion(producto, "Tocineta", 4000);

        assertEquals(22000, producto.getPrecio(), 0.01);
    }

    @Test
    void rechazarPrecioNegativo() {
        Producto producto = new ProductoBase("Cafe", 4000);

        assertThrows(IllegalArgumentException.class,
                () -> new Adicion(producto, "Leche", -1000));
    }
}
