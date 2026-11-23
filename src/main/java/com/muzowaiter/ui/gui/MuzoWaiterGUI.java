package com.muzowaiter.ui.gui;

import com.muzowaiter.model.*;
import com.muzowaiter.patterns.*;
import com.muzowaiter.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class MuzoWaiterGUI extends JFrame {

    private final GestorPedidos gestor = new GestorPedidos();
    private final CajaRegistradora caja =
            CajaRegistradora.getInstancia();

    private EstrategiaPromocion promocion;

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Productos", "Estado", "Total"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    private final JTable tabla = new JTable(modelo);
    private final JTextArea avisos = new JTextArea(7, 35);
    private final JLabel totalCaja = new JLabel("Total caja: $0");

    private final Color fondo = new Color(245, 247, 250);
    private final Color principal = new Color(37, 57, 85);

    public MuzoWaiterGUI() {
        super("muzoWaiter - Sistema de Restaurante");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);

        configurarObservadores();
        construirInterfaz();
        actualizarTabla();
    }

    private void configurarObservadores() {
        gestor.agregarObservador(p ->
                agregarAviso("COCINA", p));
        gestor.agregarObservador(p ->
                agregarAviso("CAJA", p));
        gestor.agregarObservador(p ->
                agregarAviso("MESERO", p));
    }

    private void agregarAviso(String destino, Pedido pedido) {
        avisos.append("[" + destino + "] Pedido #"
                + pedido.getId() + " - "
                + pedido.getEstado() + "\n");
        avisos.setCaretPosition(avisos.getDocument().getLength());
    }

    private JButton boton(String texto, Runnable accion) {
        JButton b = new JButton(texto);
        b.setBackground(principal);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        b.addActionListener(e -> {
            try {
                accion.run();
                actualizarTabla();
                actualizarCaja();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return b;
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBackground(fondo);
        raiz.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setContentPane(raiz);

        JLabel titulo = new JLabel("muzoWaiter | Panel de pedidos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(principal);
        raiz.add(titulo, BorderLayout.NORTH);

        tabla.setRowHeight(30);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Pedidos"));

        JPanel botones = new JPanel(new GridLayout(0, 2, 10, 10));
        botones.setBackground(fondo);

        botones.add(boton("Nuevo pedido", this::crearPedido));
        botones.add(boton("Cambiar estado", this::cambiarEstado));
        botones.add(boton("Seleccionar promocion", this::seleccionarPromocion));
        botones.add(boton("Cobrar pedido", this::cobrar));
        botones.add(boton("Consultar caja", this::consultarCaja));
        botones.add(boton("Cerrar caja", this::cerrarCaja));
        botones.add(boton("Abrir caja", this::abrirCaja));
        botones.add(boton("Ver detalle", this::verDetalle));

        JPanel centro = new JPanel(new BorderLayout(12, 12));
        centro.setOpaque(false);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(botones, BorderLayout.EAST);
        raiz.add(centro, BorderLayout.CENTER);

        avisos.setEditable(false);
        avisos.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel inferior = new JPanel(new BorderLayout(8, 8));
        inferior.setOpaque(false);
        inferior.add(totalCaja, BorderLayout.NORTH);
        inferior.add(new JScrollPane(avisos), BorderLayout.CENTER);
        inferior.setBorder(
                BorderFactory.createTitledBorder("Notificaciones"));

        raiz.add(inferior, BorderLayout.SOUTH);
        actualizarCaja();
    }

    private Producto seleccionarProducto() {
        String[] opciones = {
            "Hamburguesa - $15000",
            "Pizza - $18000",
            "Gaseosa - $5000",
            "Cafe - $4000",
            "Papas - $7000"
        };

        int opcion = JOptionPane.showOptionDialog(
                this, "Selecciona un producto",
                "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        Producto producto = switch (opcion) {
            case 0 -> new ProductoBase("Hamburguesa", 15000);
            case 1 -> new ProductoBase("Pizza", 18000);
            case 2 -> new ProductoBase("Gaseosa", 5000);
            case 3 -> new ProductoBase("Cafe", 4000);
            case 4 -> new ProductoBase("Papas", 7000);
            default -> null;
        };

        if (producto == null) return null;

        String[] adiciones = {
            "Queso", "Tocineta", "Salsa"
        };

        while (true) {
            int agregar = JOptionPane.showConfirmDialog(
                    this, "¿Agregar una adicion a "
                            + producto.getDescripcion() + "?",
                    "Adiciones", JOptionPane.YES_NO_OPTION);

            if (agregar != JOptionPane.YES_OPTION) break;

            String adicion = (String) JOptionPane.showInputDialog(
                    this, "Selecciona una adicion",
                    "Adiciones", JOptionPane.QUESTION_MESSAGE,
                    null, adiciones, adiciones[0]);

            if (adicion == null) break;

            double precio = switch (adicion) {
                case "Queso" -> 3000;
                case "Tocineta" -> 4000;
                case "Salsa" -> 1000;
                default -> 0;
            };

            producto = new Adicion(producto, adicion, precio);
        }

        return producto;
    }

    private Producto crearCombo() {
        String nombre = JOptionPane.showInputDialog(
                this, "Nombre del combo:");

        if (nombre == null) return null;

        if (nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }

        ComboBuilder builder = new ComboBuilder(nombre);
        int cantidad = 0;

        while (true) {
            Producto producto = seleccionarProducto();

            if (producto != null) {
                builder.agregarProducto(producto);
                cantidad++;
            }

            int continuar = JOptionPane.showConfirmDialog(
                    this, "¿Agregar otro producto al combo?",
                    "Combo", JOptionPane.YES_NO_OPTION);

            if (continuar != JOptionPane.YES_OPTION) break;
        }

        if (cantidad == 0) return null;

        return builder.construir();
    }

    private void crearPedido() {
        Pedido pedido = gestor.crearPedido();

        while (true) {
            String[] opciones = {
                "Producto", "Combo", "Finalizar"
            };

            int opcion = JOptionPane.showOptionDialog(
                    this, "Pedido #" + pedido.getId(),
                    "Nuevo pedido", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);

            if (opcion == 2 || opcion == -1) break;

            Producto producto = switch (opcion) {
                case 0 -> seleccionarProducto();
                case 1 -> crearCombo();
                default -> null;
            };

            if (producto != null) {
                pedido.agregarProducto(producto);
            }
        }

        JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getId()
                        + "\nTotal: $" + pedido.getTotal());
    }

    private Pedido pedidoSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            throw new IllegalStateException(
                    "Selecciona un pedido en la tabla");
        }

        int filaModelo = tabla.convertRowIndexToModel(fila);
        int id = (int) modelo.getValueAt(filaModelo, 0);

        return gestor.listarPedidos().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    private void cambiarEstado() {
        Pedido pedido = pedidoSeleccionado();

        EstadoPedido[] estados = {
            EstadoPedido.EN_PREPARACION,
            EstadoPedido.LISTO,
            EstadoPedido.ENTREGADO,
            EstadoPedido.CANCELADO
        };

        EstadoPedido estado = (EstadoPedido)
                JOptionPane.showInputDialog(
                        this, "Estado actual: " + pedido.getEstado(),
                        "Cambiar estado",
                        JOptionPane.QUESTION_MESSAGE,
                        null, estados, estados[0]);

        if (estado != null) {
            gestor.cambiarEstado(pedido, estado);
        }
    }

    private void seleccionarPromocion() {
        String[] opciones = {
            "Sin promocion",
            "10% de 14:00 a 17:00",
            "15% los miercoles"
        };

        int opcion = JOptionPane.showOptionDialog(
                this, "Selecciona una promocion",
                "Promociones", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        switch (opcion) {
            case 0 -> promocion = null;
            case 1 -> promocion = new PromocionHorario();
            case 2 -> promocion = new PromocionDia();
            default -> { return; }
        }

        JOptionPane.showMessageDialog(this,
                "Promocion actualizada");
    }

    private void cobrar() {
        Pedido pedido = pedidoSeleccionado();

        double total = pedido.getTotal();

        if (promocion != null) {
            total = promocion.aplicarDescuento(
                    total, LocalDateTime.now());
        }

        String[] metodos = {
            "EFECTIVO", "TARJETA", "TRANSFERENCIA"
        };

        String metodo = (String) JOptionPane.showInputDialog(
                this, "Total a cobrar: $" + total,
                "Registrar pago",
                JOptionPane.QUESTION_MESSAGE,
                null, metodos, metodos[0]);

        if (metodo == null) return;

        caja.registrarPago(pedido, total, metodo, promocion);

        JOptionPane.showMessageDialog(this,
                "Pago registrado correctamente");
    }

    private void consultarCaja() {
        StringBuilder sb = new StringBuilder();

        sb.append("Caja abierta: ")
                .append(caja.estaAbierta()).append("\n\n");

        for (RegistroPago pago : caja.obtenerPagos()) {
            sb.append("Pedido #").append(pago.pedidoId())
                    .append(" | ").append(pago.metodo())
                    .append(" | $").append(pago.monto())
                    .append("\n");
        }

        sb.append("\nTOTAL: $").append(caja.obtenerTotal());

        JOptionPane.showMessageDialog(this, sb.toString(),
                "Caja registradora", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cerrarCaja() {
        try {
            ReporteCaja reporte = caja.cerrarCaja();
            Path archivo = ExportadorReporte.exportar(reporte);

            JOptionPane.showMessageDialog(this,
                    reporte.generarTexto()
                            + "\nArchivo: " + archivo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void abrirCaja() {
        caja.abrirCaja();
        JOptionPane.showMessageDialog(this,
                "Caja abierta correctamente");
    }

    private void verDetalle() {
        Pedido pedido = pedidoSeleccionado();

        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(pedido.getId()).append("\n");
        sb.append("Estado: ").append(pedido.getEstado()).append("\n\n");

        for (Producto p : pedido.getProductos()) {
            sb.append(p.getDescripcion())
                    .append(" - $").append(p.getPrecio())
                    .append("\n");
        }

        sb.append("\nTOTAL: $").append(pedido.getTotal());

        JOptionPane.showMessageDialog(this, sb.toString(),
                "Detalle del pedido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);

        for (Pedido pedido : gestor.listarPedidos()) {
            modelo.addRow(new Object[]{
                pedido.getId(),
                pedido.getProductos().size(),
                pedido.getEstado(),
                pedido.getTotal()
            });
        }
    }

    private void actualizarCaja() {
        totalCaja.setText(
                "Total caja: $" + caja.obtenerTotal()
                        + " | Estado: "
                        + (caja.estaAbierta() ? "ABIERTA" : "CERRADA"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MuzoWaiterGUI().setVisible(true));
    }
}
