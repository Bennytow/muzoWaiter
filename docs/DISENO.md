# Documento técnico de diseño — muzoWaiter

## 1. Introducción

muzoWaiter es un sistema de gestión de pedidos orientado a
cafeterías y restaurantes. Su diseño busca separar las
responsabilidades del software, facilitar su mantenimiento
y permitir la incorporación de nuevas funcionalidades.

## 2. Objetivo general

Desarrollar un sistema funcional de gestión de pedidos
mediante Java, aplicando patrones de diseño y un estilo
arquitectónico que favorezca la reutilización del código.

## 3. Estilo arquitectónico

### Arquitectura en capas

Se seleccionó una arquitectura en capas flexible porque
permite separar la presentación, los servicios y el modelo
de dominio.

### Capa de presentación

Paquetes:

- ui.console
- ui.gui

Responsable de recibir las acciones del usuario y mostrar
los resultados del sistema.

### Capa de servicios

Paquete: service.

Coordina las operaciones relacionadas con pedidos,
promociones, pagos, notificaciones y reportes.

### Capa de dominio

Paquete: model.

Contiene las entidades que representan los conceptos
principales del restaurante, como Producto, Pedido,
Combo, RegistroPago y ReporteCaja.

### Componentes de patrones

Paquete: patterns.

Contiene abstracciones e implementaciones reutilizables
que apoyan la lógica del negocio.

### Justificación

La arquitectura permite desarrollar dos interfaces
diferentes reutilizando las mismas clases de negocio.

Se considera flexible porque la presentación también
puede acceder directamente a las entidades del dominio.

### Ventajas

- Separación de responsabilidades.
- Reutilización del código.
- Facilidad de mantenimiento.
- Mayor facilidad para realizar pruebas.
- Posibilidad de incorporar nuevas interfaces.

### Desventajas

- Mayor cantidad de clases.
- Necesidad de controlar las dependencias.
- Posible duplicación de lógica entre interfaces.
- Mayor complejidad inicial frente a un programa simple.

## 4. Patrones de diseño

### 4.1 Decorator

Categoría: estructural.

Problema:
Los productos pueden tener múltiples adiciones que
modifican su descripción y precio.

Solución:
Envolver un producto mediante objetos Adicion que
implementan la misma interfaz Producto.

Participantes:

- Producto: componente.
- ProductoBase: componente concreto.
- ProductoDecorator: decorador abstracto.
- Adicion: decorador concreto.

Ejemplo:

Una hamburguesa de $15000 recibe queso por $3000
y tocineta por $4000. El resultado es $22000.

Beneficio:
Evita crear una subclase por cada combinación de
ingredientes y permite extender el comportamiento.

### 4.2 Observer

Categoría: comportamiento.

Problema:
Cocina, caja y mesero necesitan conocer los cambios
de estado de los pedidos.

Solución:
GestorPedidos mantiene una colección de observadores
y les comunica los cambios mediante actualizar().

Participantes:

- GestorPedidos: sujeto.
- ObservadorPedido: observador abstracto.
- NotificadorCocina: observador concreto.
- NotificadorCaja: observador concreto.
- NotificadorMesero: observador concreto.

Beneficio:
Reduce el acoplamiento entre la gestión de pedidos
y los componentes encargados de recibir avisos.

### 4.3 Strategy

Categoría: comportamiento.

Problema:
El restaurante necesita diferentes reglas de descuento.

Solución:
Definir una interfaz común para los algoritmos
de promociones y seleccionar una estrategia.

Participantes:

- EstrategiaPromocion: interfaz.
- PromocionHorario: estrategia concreta.
- PromocionDia: estrategia concreta.
- ServicioPromociones: contexto reutilizable.

Promociones:

- 10% entre las 14:00 y las 17:00.
- 15% los miércoles.

Beneficio:
Permite agregar nuevas promociones sin modificar
las implementaciones existentes.

### 4.4 Builder

Categoría: creacional.

Problema:
Los combos pueden estar formados por diferentes
cantidades y combinaciones de productos.

Solución:
Utilizar ComboBuilder para agregar productos
progresivamente y construir el objeto Combo.

Participantes:

- ComboBuilder: constructor.
- Combo: producto construido.
- Producto: componente del combo.

Beneficio:
Separa el proceso de construcción de la representación
final del objeto.

### 4.5 Singleton

Categoría: creacional.

Problema:
Se necesita centralizar las operaciones de caja dentro
de una ejecución de la aplicación.

Solución:
CajaRegistradora tiene un constructor privado,
una instancia estática y un método getInstancia().

Beneficio:
Centraliza el registro de pagos y el cierre de caja.

Limitación:
La instancia es única dentro de la JVM, no entre
procesos diferentes ni equipos conectados en red.

## 5. Principios SOLID

### Responsabilidad única

Las clases se organizan según sus responsabilidades:
productos, pedidos, promociones, notificaciones y caja.

### Abierto y cerrado

Decorator y Strategy permiten incorporar nuevas
adiciones y promociones mediante extensiones.

### Sustitución de Liskov

ProductoBase, Combo y Adicion pueden utilizarse
a través del contrato Producto.

### Segregación de interfaces

Las interfaces Producto, ObservadorPedido y
EstrategiaPromocion contienen operaciones específicas.

### Inversión de dependencias

Los componentes utilizan abstracciones como Producto,
ObservadorPedido y EstrategiaPromocion.

Estos principios se aplican parcialmente; el sistema
todavía contiene dependencias directas entre clases
concretas que podrían mejorarse.

## 6. Pruebas unitarias

Se utiliza JUnit 5 para verificar:

- Decorator: acumulación de adiciones y precios.
- Observer: notificaciones y cambios de estado.
- Strategy: descuentos por horario y día.
- Builder: construcción y validación de combos.
- Singleton: identidad de instancia y validación de pagos.

Las pruebas se ejecutan mediante:

    mvn clean test

## 7. Diagramas UML

Los diagramas se encuentran en docs/uml.

Incluyen casos de uso, clases, secuencia, actividades,
paquetes y representación de los cinco patrones.

## 8. Decisiones y limitaciones

Se eligió una aplicación de escritorio para reducir
la complejidad de infraestructura del proyecto.

Los pedidos activos se mantienen en memoria.
Los reportes se guardan como archivos TXT.

No se garantiza la recuperación de pedidos y pagos
después de reiniciar la aplicación.

Las interfaces comparten código, pero no sincronizan
sus instancias cuando se ejecutan por separado.

Como mejoras futuras se propone incorporar:

- Persistencia local de pedidos y pagos.
- Catálogo de productos configurable.
- Autenticación y permisos por roles.
- Uso de BigDecimal para operaciones monetarias.
- Sincronización entre diferentes terminales.

## 9. Conclusión

muzoWaiter demuestra la aplicación práctica de cinco
patrones pertenecientes a tres categorías de diseño.

La arquitectura en capas permite reutilizar el núcleo
de negocio mediante diferentes interfaces y facilita
la evolución del sistema.
