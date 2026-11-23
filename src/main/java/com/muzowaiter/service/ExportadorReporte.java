package com.muzowaiter.service;

import com.muzowaiter.model.ReporteCaja;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ExportadorReporte {

    public static Path exportar(ReporteCaja reporte)
            throws IOException {

        Path carpeta = Path.of("reportes");
        Files.createDirectories(carpeta);

        Path archivo = carpeta.resolve(
                "cierre_" + reporte.fecha() + ".txt");

        Files.writeString(
                archivo,
                reporte.generarTexto(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW,
                StandardOpenOption.WRITE
        );

        return archivo;
    }
}
