package com.muzowaiter.patterns;

import com.muzowaiter.model.Producto;

public abstract class ProductoDecorator implements Producto {

    protected final Producto producto;

    public ProductoDecorator(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        this.producto = producto;
    }
}
