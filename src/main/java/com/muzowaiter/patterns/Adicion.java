package com.muzowaiter.patterns;

import com.muzowaiter.model.Producto;

public class Adicion extends ProductoDecorator {

    private final String nombre;
    private final double precio;

    public Adicion(Producto producto, String nombre, double precio) {
        super(producto);
        if (nombre == null || nombre.isBlank() || !Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("Adicion invalida");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String getDescripcion() {
        return producto.getDescripcion() + " + " + nombre;
    }

    @Override
    public double getPrecio() {
        return producto.getPrecio() + precio;
    }
}
