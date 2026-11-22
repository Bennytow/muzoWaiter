package com.muzowaiter.model;

import java.time.LocalDate;
import java.util.List;

public record ReporteCaja(
        LocalDate fecha,
        List<RegistroPago> pagos,
        double total
) {
    public ReporteCaja {
        pagos = List.copyOf(pagos);
    }

    public int cantidadVentas() {
        return pagos.size();
    }

    public String generarTexto() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n===== CIERRE DE CAJA =====\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Ventas: ").append(cantidadVentas()).append("\n");

        for (RegistroPago pago : pagos) {
            sb.append("Pedido #").append(pago.pedidoId())
              .append(" | ").append(pago.metodo())
              .append(" | $").append(pago.monto())
              .append("\n");
        }

        sb.append("TOTAL RECAUDADO: $").append(total).append("\n");
        sb.append("=========================\n");

        return sb.toString();
    }
}
