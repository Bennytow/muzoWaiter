package com.muzowaiter.patterns;

import com.muzowaiter.model.Combo;
import com.muzowaiter.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ComboBuilder {

    private final String nombre;
    private final List<Producto> productos = new ArrayList<>();

    public ComboBuilder(String nombre) {
        this.nombre = nombre;
    }

    public ComboBuilder agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("Producto invalido");
        }
        productos.add(producto);
        return this;
    }

    public Combo construir() {
        return new Combo(nombre, productos);
    }
}
