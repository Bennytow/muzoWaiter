
#Muzo Waiter

### Sistema de Gestión de Pedidos para Cafeterías y Restaurantes

**Proyecto académico — Ingeniería de Software**

Muzo Waiter es un sistema de gestión de pedidos desarrollado en **Java 21**, orientado a cafeterías y restaurantes que necesitan administrar productos personalizables, combos, pedidos, promociones y ventas.

El proyecto busca aplicar los principios de **Programación Orientada a Objetos (POO)**, **SOLID** y **patrones de diseño**, construyendo una solución modular, mantenible y extensible.

---

## 📋 1. Descripción del proyecto

**Propuesta 3: Cafetería o restaurante**

El sistema permite gestionar pedidos de clientes mediante un menú configurable, ofreciendo opciones para personalizar bebidas y platos, construir combos y administrar el flujo de atención.

También contempla notificaciones automáticas entre los diferentes roles del restaurante, promociones y generación de reportes de ventas.

### Funcionalidades previstas

- 🍔 Registro y administración de platos y bebidas.
- 🧀 Personalización de productos mediante adiciones.
- 🍟 Construcción de combos paso a paso.
- 📖 Administración de un menú configurable.
- 🧾 Registro de pedidos por mesa o domicilio.
- 👨‍🍳 Notificaciones automáticas a cocina, caja y meseros.
- 🏷️ Aplicación de promociones por horario o día.
- 💰 Cierre de caja y generación de reportes.
- 💻 Interfaz por consola.
- 🖥️ Interfaz gráfica sencilla.

---

## 🎯 2. Objetivos

### Objetivo general

Desarrollar un sistema de gestión de pedidos para cafeterías y restaurantes mediante Java, aplicando patrones de diseño y principios de ingeniería de software para garantizar una arquitectura modular, reutilizable y extensible.

### Objetivos específicos

1. Implementar un menú configurable con diferentes categorías de productos.
2. Permitir la personalización de platos y bebidas mediante adiciones.
3. Facilitar la construcción de combos con múltiples opciones.
4. Administrar pedidos y sus respectivos estados.
5. Notificar automáticamente los cambios de estado de los pedidos.
6. Implementar promociones basadas en fechas y horarios.
7. Generar reportes de ventas y cierres de caja.
8. Desarrollar interfaces de consola y gráfica sobre un mismo núcleo.
9. Aplicar patrones de diseño creacionales, estructurales y de comportamiento.
10. Validar las funcionalidades mediante pruebas unitarias.

---

## ⚙️ 3. Tecnologías

| Tecnología | Propósito |
|------------|-----------|
| Java 21 | Lenguaje principal |
| Java Swing | Interfaz gráfica propuesta |
| Maven | Gestión de dependencias y compilación |
| JUnit 5 | Pruebas unitarias |
| Git | Control de versiones |
| GitHub | Repositorio y colaboración |
| PlantUML | Diagramas UML |
| Debian 13 | Entorno de desarrollo y ejecución |

---

## 🏗️ 4. Arquitectura del sistema

El sistema se plantea bajo una arquitectura modular que separa las responsabilidades de presentación, negocio y persistencia.

### Capas propuestas

**Presentación**

Contiene las interfaces mediante las cuales los usuarios interactúan con el sistema.

- Interfaz de consola.
- Interfaz gráfica con Java Swing.

**Lógica de negocio**

Contiene las reglas y operaciones principales.

- Gestión de productos.
- Personalización de pedidos.
- Construcción de combos.
- Aplicación de promociones.
- Administración de estados.
- Generación de reportes.

**Persistencia**

Responsable de almacenar y recuperar información.

- Productos.
- Pedidos.
- Ventas.
- Configuración del menú.

### Estructura de paquetes propuesta

```text
muzoWaiter/
├── pom.xml
├── README.md
├── docs/
│   ├── diagramas/
│   └── diseno/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── muzowaiter/
│   │               ├── Main.java
│   │               ├── modelo/
│   │               ├── servicio/
│   │               ├── repositorio/
│   │               ├── patrones/
│   │               │   ├── factory/
│   │               │   ├── decorator/
│   │               │   └── observer/
│   │               ├── consola/
│   │               ├── gui/
│   │               └── util/
│   └── test/
│       └── java/
│           └── com/
│               └── muzowaiter/
```

> Esta estructura es una referencia arquitectónica y debe ajustarse a los paquetes reales del repositorio.

---

## 🧩 5. Patrones de diseño

El proyecto contempla la aplicación de patrones de diseño pertenecientes a tres categorías diferentes.

### 5.1 Factory Method — Creacional

**Problema:**

El restaurante necesita crear diferentes tipos de productos sin depender directamente de sus clases concretas.

**Solución propuesta:**

Utilizar Factory Method para delegar la creación de productos a clases especializadas.

**Aplicación:**

- Creación de bebidas.
- Creación de platos.
- Incorporación de nuevos tipos de productos.

**Ventajas:**

- Reduce el acoplamiento.
- Facilita agregar productos.
- Favorece el principio abierto/cerrado (OCP).

### 5.2 Decorator — Estructural

**Problema:**

Los clientes pueden agregar ingredientes o complementos que modifican el precio y la descripción de un producto.

**Solución propuesta:**

Utilizar Decorator para agregar responsabilidades dinámicamente sin modificar la clase original.

**Ejemplo:**

```text
Hamburguesa base:        $15.000
+ Queso:                 $2.000
+ Tocineta:              $3.500
--------------------------------
Total:                  $20.500
```

**Ventajas:**

- Permite combinar adiciones.
- Evita crear demasiadas subclases.
- Facilita incorporar nuevas personalizaciones.

### 5.3 Observer — Comportamiento

**Problema:**

Cuando cambia el estado de un pedido, diferentes áreas del restaurante deben recibir una notificación.

**Solución propuesta:**

Implementar Observer para que cocina, caja y meseros puedan suscribirse a los cambios de estado.

**Ejemplo:**

```text
PEDIDO #001
     |
     v
Estado: EN PREPARACIÓN
     |
     +----> Cocina: preparar pedido
     |
     +----> Caja: actualizar estado
     |
     +----> Mesero: informar progreso
```

**Ventajas:**

- Desacopla las notificaciones.
- Facilita agregar nuevos observadores.
- Permite actualizar múltiples áreas automáticamente.

### Otros patrones que pueden evaluarse

| Patrón | Categoría | Posible aplicación |
|--------|-----------|-------------------|
| Builder | Creacional | Construcción paso a paso de combos |
| Strategy | Comportamiento | Promociones y descuentos |
| State | Comportamiento | Estados de los pedidos |

La implementación definitiva de estos patrones dependerá del diseño y las clases desarrolladas.

---

## 📌 6. Requisitos funcionales

| Código | Descripción |
|--------|-------------|
| RF01 | Registrar productos base, como platos y bebidas, asociados a un menú configurable. |
| RF02 | Agregar adiciones opcionales a los productos, actualizando automáticamente precio y descripción. |
| RF03 | Construir combos interactivamente, validando las restricciones de cada categoría. |
| RF04 | Registrar pedidos vinculados a una mesa o servicio a domicilio. |
| RF05 | Notificar automáticamente a cocina, caja y meseros cuando cambie el estado de un pedido. |
| RF06 | Aplicar promociones automáticas según el horario o día de la semana. |
| RF07 | Realizar cierres de caja y generar reportes de ventas totales y por categoría. |

---

## 🔒 7. Requisitos no funcionales

| Código | Descripción |
|--------|-------------|
| RNF01 | El sistema debe ejecutarse en Debian 13 utilizando Java 21. |
| RNF02 | La lógica de negocio debe estar desacoplada de las interfaces de consola y gráfica. |
| RNF03 | Implementar al menos tres patrones de diseño de categorías diferentes. |
| RNF04 | Organizar el código en paquetes con responsabilidades claras y documentación UML coherente. |

### Criterios de calidad

- Modularidad.
- Reutilización.
- Mantenibilidad.
- Extensibilidad.
- Legibilidad.
- Separación de responsabilidades.
- Facilidad para realizar pruebas.

---

## 🚀 8. Instalación y ejecución

### Requisitos previos

Para compilar el proyecto se requiere:

- Java Development Kit (JDK) 21.
- Apache Maven, si el proyecto utiliza Maven.
- Git.

### 8.1 Verificar Java

```bash
java -version
```

### 8.2 Verificar Maven

```bash
mvn -version
```

### 8.3 Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
cd muzoWaiter
```

Reemplazar `URL_DEL_REPOSITORIO` por la dirección real del proyecto.

### 8.4 Compilar con Maven

Si el repositorio contiene un archivo `pom.xml`:

```bash
mvn clean compile
```

### 8.5 Ejecutar pruebas

```bash
mvn test
```

### 8.6 Generar el archivo JAR

```bash
mvn clean package
```

### 8.7 Ejecutar la aplicación

El comando definitivo dependerá de la clase principal y de la configuración del proyecto.

Si se genera un JAR ejecutable:

```bash
java -jar target/NOMBRE_DEL_ARCHIVO.jar
```

> Los comandos de ejecución deben verificarse con la configuración real del repositorio.

---

## 🖥️ 9. Interfaces de usuario

El proyecto contempla dos formas de interacción con el sistema.

### Interfaz por consola

Permite realizar las operaciones principales mediante un menú de texto.

Ejemplo conceptual:

```text
==================================
          MUZO WAITER
==================================

1. Ver menú
2. Registrar pedido
3. Personalizar producto
4. Crear combo
5. Consultar pedidos
6. Actualizar estado
7. Consultar promociones
8. Generar reporte de ventas
9. Cerrar caja
0. Salir

Seleccione una opción:
```

### Interfaz gráfica

La interfaz gráfica se plantea utilizando Java Swing.

Debe permitir:

- Consultar productos.
- Seleccionar adiciones.
- Crear combos.
- Registrar pedidos.
- Visualizar estados.
- Consultar reportes.

Ambas interfaces deben compartir los mismos servicios y reglas de negocio.

---

## 📊 10. Diagramas UML

La documentación del proyecto debe incluir los siguientes diagramas:

| Diagrama | Descripción |
|----------|-------------|
| Casos de uso | Interacciones de los actores con el sistema |
| Clases general | Estructura y relaciones principales |
| Clases Factory Method | Creación de productos |
| Clases Decorator | Personalización de productos |
| Clases Observer | Notificaciones de pedidos |
| Secuencia | Flujo principal de registro y actualización de un pedido |

Los diagramas definitivos deben reflejar las clases, métodos y relaciones existentes en el código fuente.

---

## 🧪 11. Pruebas unitarias

Se contempla el uso de JUnit 5 para verificar el funcionamiento de las clases principales y los patrones implementados.

### Pruebas previstas

**Factory Method**

- Verificar la creación de productos.
- Comprobar los tipos de producto generados.

**Decorator**

- Verificar la actualización del precio.
- Comprobar la descripción del producto.
- Validar combinaciones de adiciones.

**Observer**

- Verificar el registro de observadores.
- Comprobar el envío de notificaciones.
- Validar los cambios de estado.

**Reglas de negocio**

- Validación de combos.
- Cálculo de promociones.
- Totalización de pedidos.
- Cálculo de ventas.

Para ejecutar las pruebas en un proyecto Maven:

```bash
mvn test
```

---

## 📚 12. Documento de diseño

El documento de diseño debe justificar las decisiones arquitectónicas tomadas durante el desarrollo.

Para cada patrón implementado se documentará:

1. Problema concreto que motivó su utilización.
2. Explicación del patrón con palabras propias.
3. Correspondencia entre roles del patrón y clases reales.
4. Alternativas consideradas y razones para descartarlas.
5. Ventajas y desventajas.
6. Relación con los principios SOLID.

### Principios SOLID considerados

- **S — Single Responsibility:** cada clase debe tener una responsabilidad principal.
- **O — Open/Closed:** el sistema debe poder extenderse sin modificar innecesariamente código existente.
- **L — Liskov Substitution:** las implementaciones deben respetar los contratos de sus abstracciones.
- **I — Interface Segregation:** las interfaces deben contener únicamente las operaciones necesarias.
- **D — Dependency Inversion:** los módulos de alto nivel deben depender de abstracciones.

El documento finalizará con las conclusiones, los aprendizajes obtenidos y las posibles mejoras del diseño.

---

## 🎓 13. Sustentación del proyecto

La sustentación académica tendrá una duración aproximada de **10 minutos por grupo**.

### Actividades

1. Presentación general del sistema.
2. Demostración del funcionamiento.
3. Explicación de la arquitectura.
4. Presentación de los patrones de diseño.
5. Recorrido por los diagramas UML.
6. Demostración de pruebas unitarias.
7. Preguntas individuales a los integrantes.

El diseño debe permitir demostrar su extensibilidad mediante cambios de requisitos, como agregar un nuevo tipo de producto, una adición o una promoción.

---

## 📈 14. Estado del desarrollo

La siguiente lista permite registrar el progreso del proyecto. Las casillas deben marcarse únicamente cuando la funcionalidad esté terminada y verificada.

### Funcionalidades

- [ ] Gestión de productos y menú.
- [ ] Personalización de platos y bebidas.
- [ ] Construcción de combos.
- [ ] Registro de pedidos.
- [ ] Administración de estados.
- [ ] Notificaciones automáticas.
- [ ] Promociones.
- [ ] Reportes de ventas.
- [ ] Cierre de caja.

### Arquitectura y calidad

- [ ] Interfaz por consola.
- [ ] Interfaz gráfica.
- [ ] Núcleo compartido.
- [ ] Patrón creacional.
- [ ] Patrón estructural.
- [ ] Patrón de comportamiento.
- [ ] Pruebas unitarias.
- [ ] Diagramas UML.
- [ ] Documento de diseño.
- [ ] Sustentación final.

---

## 🔮 15. Posibles mejoras futuras

- Integración con una base de datos relacional.
- Administración de inventario.
- Control de usuarios y permisos.
- Historial de pedidos.
- Estadísticas de productos más vendidos.
- Generación de facturas.
- Exportación de reportes a PDF.
- Integración con servicios de domicilios.
- Implementación de una interfaz web.

---

## 📝 16. Conclusiones

Muzo Waiter busca demostrar cómo los patrones de diseño permiten resolver problemas concretos en un sistema de gestión de pedidos.

La separación de responsabilidades facilita la incorporación de nuevos productos, reglas de promoción y mecanismos de notificación sin afectar innecesariamente otros componentes.

El proyecto también permite fortalecer conocimientos de programación orientada a objetos, arquitectura de software, pruebas unitarias y principios SOLID.

---

## 👥 17. Equipo de desarrollo

**Proyecto académico de Ingeniería de Software**

Universidad Libre — Ingeniería de Sistemas

**Integrantes:** Por completar.

---

## 📄 18. Licencia

La licencia del proyecto está pendiente de definición.

---

**Muzo Waiter — Sistema de Gestión de Pedidos para Cafeterías y Restaurantes.**
