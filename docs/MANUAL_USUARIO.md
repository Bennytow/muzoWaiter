# Manual de usuario — muzoWaiter

## 1. Requisitos

- Java 21.
- Maven para compilar el proyecto.
- Entorno gráfico para utilizar Swing.

## 2. Iniciar el programa

Compilar:

    mvn clean package

Ejecutar la interfaz gráfica:

    java -jar target/muzoWaiter-1.0.0.jar

Ejecutar la consola:

    java -cp target/classes com.muzowaiter.ui.console.ConsolaApp

## 3. Crear un pedido

1. Seleccionar Nuevo pedido.
2. Elegir un producto o combo.
3. Seleccionar el producto deseado.
4. Agregar adiciones opcionales.
5. Agregar más productos si es necesario.
6. Finalizar el pedido.

El sistema muestra el identificador y el valor total.

## 4. Menú de productos

| Producto | Precio COP |
|---|---:|
| Hamburguesa | 15000 |
| Pizza | 18000 |
| Gaseosa | 5000 |
| Café | 4000 |
| Papas | 7000 |

## 5. Adiciones

| Adición | Precio COP |
|---|---:|
| Queso | 3000 |
| Tocineta | 4000 |
| Salsa | 1000 |

## 6. Construir un combo

1. Seleccionar Nuevo pedido.
2. Elegir Combo.
3. Escribir el nombre del combo.
4. Agregar los productos deseados.
5. Finalizar la construcción.

El precio se calcula a partir de sus componentes.

## 7. Gestionar estados

Los pedidos siguen el siguiente flujo:

RECIBIDO -> EN_PREPARACION -> LISTO -> ENTREGADO

También es posible cancelar pedidos recibidos
o que se encuentren en preparación.

Cada cambio genera notificaciones para las áreas
del restaurante.

## 8. Aplicar promociones

Seleccionar la opción de promociones.

Las opciones disponibles son:

- Sin promoción.
- Descuento del 10% por horario.
- Descuento del 15% los miércoles.

Las promociones dependen de la fecha y hora del
equipo y no son acumulables.

## 9. Registrar un pago

1. Seleccionar un pedido entregado.
2. Elegir Cobrar pedido.
3. Revisar el valor total.
4. Seleccionar el método de pago.
5. Confirmar la operación.

Métodos disponibles:

- Efectivo.
- Tarjeta.
- Transferencia.

## 10. Consultar caja

Seleccionar Consultar caja para visualizar los
pagos registrados y el total de ventas.

## 11. Cerrar caja

Seleccionar Cerrar caja.

El sistema genera un reporte en la carpeta:

    reportes/

Formato del archivo:

    cierre_AAAA-MM-DD.txt

No se permite sobrescribir un reporte existente.

La reapertura está prevista para un nuevo día.

## 12. Recomendaciones

No cerrar la aplicación antes de finalizar las
operaciones pendientes.

Conservar los reportes generados.

Los pedidos activos no se recuperan al reiniciar
el programa.

## 13. Solución de problemas

### El programa no compila

Verificar la versión de Java:

    java -version

### No aparece la interfaz gráfica

Verificar que se está utilizando un entorno de
escritorio y ejecutar nuevamente el archivo JAR.

### No se puede cobrar un pedido

Comprobar que su estado sea ENTREGADO, que la caja
esté abierta y que el pedido no haya sido cobrado.

### No se puede exportar el reporte

Comprobar los permisos de escritura y verificar
si ya existe un reporte para esa fecha.
