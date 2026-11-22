package com.muzowaiter.model;

import java.util.List;

public class Combo implements Producto {

    private final String nombre;
    private final List<Producto> productos;

    public Combo(String nombre, List<Producto> productos) {
        if (nombre == null || nombre.isBlank()
                || productos == null || productos.isEmpty()
                || productos.stream().anyMatch(p -> p == null)) {
            throw new IllegalArgumentException("Combo invalido");
        }

        this.nombre = nombre;
        this.productos = List.copyOf(productos);
    }

    @Override
    public String getDescripcion() {
        StringBuilder descripcion = new StringBuilder(nombre + ": ");

        for (Producto producto : productos) {
            descripcion.append(producto.getDescripcion()).append(", ");
        }

        descripcion.setLength(descripcion.length() - 2);
        return descripcion.toString();
    }

    @Override
    public double getPrecio() {
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
