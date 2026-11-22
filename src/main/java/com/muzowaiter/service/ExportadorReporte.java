package com.muzowaiter.service;

import com.muzowaiter.model.ReporteCaja;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExportadorReporte {

    public static Path exportar(ReporteCaja reporte)
            throws IOException {

        Path carpeta = Path.of("reportes");
        Files.createDirectories(carpeta);

        Path archivo = carpeta.resolve(
                "cierre_" + reporte.fecha() + ".txt"
        );

        if (Files.exists(archivo)) {
            throw new IOException(
                    "Ya existe un reporte para esta fecha: " + archivo
            );
        }

        Files.writeString(
                archivo,
                reporte.generarTexto(),
                StandardCharsets.UTF_8
        );

        return archivo;
    }
}
