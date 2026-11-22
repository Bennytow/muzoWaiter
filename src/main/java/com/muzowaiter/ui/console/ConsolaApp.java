package com.muzowaiter.ui.console;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.*;
import com.muzowaiter.service.*;

import java.time.LocalDateTime;
import java.util.*;

public class ConsolaApp {

    private final Scanner sc = new Scanner(System.in);
    private final GestorPedidos gestor = new GestorPedidos();
    private final CajaRegistradora caja =
            CajaRegistradora.getInstancia();

    private EstrategiaPromocion promocion;

    public ConsolaApp() {
        gestor.agregarObservador(new NotificadorCocina());
        gestor.agregarObservador(new NotificadorCaja());
        gestor.agregarObservador(new NotificadorMesero());
    }

    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n===== MUZOWAITER =====");
            System.out.println("1. Crear pedido");
            System.out.println("2. Listar pedidos");
            System.out.println("3. Cambiar estado");
            System.out.println("4. Seleccionar promocion");
            System.out.println("5. Cobrar pedido");
            System.out.println("6. Consultar caja");
            System.out.println("7. Cerrar caja");
            System.out.println("8. Abrir caja");
            System.out.println("0. Salir");

            opcion = leerEntero("Opcion: ");

            try {
                switch (opcion) {
                    case 1 -> crearPedido();
                    case 2 -> listarPedidos();
                    case 3 -> cambiarEstado();
                    case 4 -> seleccionarPromocion();
                    case 5 -> cobrar();
                    case 6 -> consultarCaja();
                    case 7 -> cerrarCaja();
                    case 8 -> abrirCaja();
                    case 0 -> System.out.println("Hasta pronto.");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un numero valido.");
            }
        }
    }

    private Producto seleccionarProducto() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Hamburguesa - $15000");
        System.out.println("2. Pizza - $18000");
        System.out.println("3. Gaseosa - $5000");
        System.out.println("4. Cafe - $4000");
        System.out.println("5. Papas - $7000");

        int opcion = leerEntero("Producto: ");

        Producto producto = switch (opcion) {
            case 1 -> new ProductoBase("Hamburguesa", 15000);
            case 2 -> new ProductoBase("Pizza", 18000);
            case 3 -> new ProductoBase("Gaseosa", 5000);
            case 4 -> new ProductoBase("Cafe", 4000);
            case 5 -> new ProductoBase("Papas", 7000);
            default -> null;
        };

        if (producto == null) {
            System.out.println("Producto invalido.");
            return null;
        }

        while (true) {
            System.out.println("\nAdiciones:");
            System.out.println("1. Queso - $3000");
            System.out.println("2. Tocineta - $4000");
            System.out.println("3. Salsa - $1000");
            System.out.println("0. Terminar adiciones");

            int adicion = leerEntero("Opcion: ");

            if (adicion == 0) break;

            switch (adicion) {
                case 1 -> producto =
                        new Adicion(producto, "Queso", 3000);
                case 2 -> producto =
                        new Adicion(producto, "Tocineta", 4000);
                case 3 -> producto =
                        new Adicion(producto, "Salsa", 1000);
                default -> System.out.println("Adicion invalida.");
            }
        }

        return producto;
    }

    private Producto crearCombo() {
        System.out.print("Nombre del combo: ");
        String nombre = sc.nextLine().trim();

        ComboBuilder builder = new ComboBuilder(nombre);
        int cantidad = 0;

        while (true) {
            System.out.println("\n1. Agregar producto al combo");
            System.out.println("0. Finalizar combo");

            int opcion = leerEntero("Opcion: ");

            if (opcion == 0) break;

            if (opcion == 1) {
                Producto producto = seleccionarProducto();

                if (producto != null) {
                    builder.agregarProducto(producto);
                    cantidad++;
                }
            }
        }

        if (cantidad == 0) {
            System.out.println("Combo vacio descartado.");
            return null;
        }

        return builder.construir();
    }

    private void crearPedido() {
        Pedido pedido = gestor.crearPedido();

        while (true) {
            System.out.println("\n--- PEDIDO #" + pedido.getId() + " ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Crear combo");
            System.out.println("0. Finalizar pedido");

            int opcion = leerEntero("Opcion: ");

            if (opcion == 0) break;

            Producto producto = switch (opcion) {
                case 1 -> seleccionarProducto();
                case 2 -> crearCombo();
                default -> null;
            };

            if (producto != null) {
                pedido.agregarProducto(producto);
                System.out.println("Agregado: "
                        + producto.getDescripcion());
            }
        }

        System.out.println("Pedido #" + pedido.getId()
                + " total: $" + pedido.getTotal());
    }

    private void listarPedidos() {
        if (gestor.listarPedidos().isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }

        for (Pedido pedido : gestor.listarPedidos()) {
            System.out.println("\nPedido #" + pedido.getId()
                    + " | " + pedido.getEstado());

            for (Producto producto : pedido.getProductos()) {
                System.out.println(" - " + producto.getDescripcion()
                        + " | $" + producto.getPrecio());
            }

            System.out.println("Total: $" + pedido.getTotal());
        }
    }

    private Pedido buscarPedido() {
        int id = leerEntero("ID del pedido: ");

        return gestor.listarPedidos().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pedido no encontrado"));
    }

    private void cambiarEstado() {
        Pedido pedido = buscarPedido();

        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("1. EN_PREPARACION");
        System.out.println("2. LISTO");
        System.out.println("3. ENTREGADO");
        System.out.println("4. CANCELADO");

        int opcion = leerEntero("Nuevo estado: ");

        EstadoPedido estado = switch (opcion) {
            case 1 -> EstadoPedido.EN_PREPARACION;
            case 2 -> EstadoPedido.LISTO;
            case 3 -> EstadoPedido.ENTREGADO;
            case 4 -> EstadoPedido.CANCELADO;
            default -> throw new IllegalArgumentException(
                    "Estado invalido");
        };

        gestor.cambiarEstado(pedido, estado);
        System.out.println("Estado actualizado.");
    }

    private void seleccionarPromocion() {
        System.out.println("1. Descuento 10% de 14:00 a 17:00");
        System.out.println("2. Descuento 15% los miercoles");
        System.out.println("0. Sin promocion");

        int opcion = leerEntero("Opcion: ");

        switch (opcion) {
            case 1 -> promocion = new PromocionHorario();
            case 2 -> promocion = new PromocionDia();
            case 0 -> promocion = null;
            default -> {
                System.out.println("Opcion invalida.");
                return;
            }
        }

        System.out.println("Promocion actualizada.");
    }

    private void cobrar() {
        Pedido pedido = buscarPedido();

        double total = pedido.getTotal();

        if (promocion != null) {
            total = promocion.aplicarDescuento(
                    total, LocalDateTime.now());
        }

        System.out.println("Total a pagar: $" + total);
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        System.out.println("3. Transferencia");

        int opcion = leerEntero("Metodo: ");

        String metodo = switch (opcion) {
            case 1 -> "EFECTIVO";
            case 2 -> "TARJETA";
            case 3 -> "TRANSFERENCIA";
            default -> throw new IllegalArgumentException(
                    "Metodo invalido");
        };

        caja.registrarPago(pedido, total, metodo, promocion);

        System.out.println("Pago registrado correctamente.");
    }

    private void consultarCaja() {
        System.out.println("\n--- CAJA ---");
        System.out.println("Abierta: " + caja.estaAbierta());

        for (RegistroPago pago : caja.obtenerPagos()) {
            System.out.println("Pedido #" + pago.pedidoId()
                    + " | " + pago.metodo()
                    + " | $" + pago.monto());
        }

        System.out.println("Total: $" + caja.obtenerTotal());
    }

    private void cerrarCaja() throws Exception {
        ReporteCaja reporte = caja.cerrarCaja();

        System.out.println(reporte.generarTexto());

        System.out.println("Reporte guardado en: "
                + ExportadorReporte.exportar(reporte));
    }

    private void abrirCaja() {
        caja.abrirCaja();
        System.out.println("Caja abierta correctamente.");
    }

    public static void main(String[] args) {
        new ConsolaApp().iniciar();
    }
}
