package com.muzowaiter;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.ComboBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @Test
    void construirComboCompleto() {
        Combo combo = new ComboBuilder("Combo Especial")
                .agregarProducto(new ProductoBase("Hamburguesa", 15000))
                .agregarProducto(new ProductoBase("Papas", 7000))
                .agregarProducto(new ProductoBase("Gaseosa", 5000))
                .construir();

        assertEquals(27000, combo.getPrecio(), 0.01);
        assertEquals(3, combo.getProductos().size());
        assertTrue(combo.getDescripcion().contains("Hamburguesa"));
    }

    @Test
    void rechazarComboVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new ComboBuilder("Combo vacio").construir());
    }

    @Test
    void comboConservaSusProductos() {
        ComboBuilder builder = new ComboBuilder("Combo")
                .agregarProducto(new ProductoBase("Cafe", 4000));

        Combo combo = builder.construir();

        builder.agregarProducto(new ProductoBase("Pizza", 18000));

        assertEquals(1, combo.getProductos().size());
        assertEquals(4000, combo.getPrecio(), 0.01);
    }
}
