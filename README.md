# Muzo Waiter

Propuesta 3. Cafetería o restaurante
Sistema de pedidos para un local con productos personalizables.
•	Bebidas y platos con adiciones opcionales que modifican el precio y la descripción.
•	Combos armados paso a paso con muchas opciones.
•	Distintos tipos de producto creados a partir de un menú configurable.
•	Aviso automático a cocina, caja y mesero cuando cambia el estado de un pedido.
•	Promociones por horario o por día.
•	Cierre de caja y reporte de ventas.


##Requisitos Funcionales (RF)

RF01: El sistema debe permitir registrar productos base (platos y bebidas) asociados a un menú configurable.

RF02: El sistema debe permitir agregar adiciones opcionales a un producto, recalculando automáticamente el costo total y actualizando su descripción.

RF03: El sistema debe permitir el armado interactivo de combos paso a paso validando las restricciones de cada categoría.

RF04: El sistema debe registrar pedidos de clientes vinculados a una mesa o servicio a domicilio.

RF05: El sistema debe notificar de forma automática a los roles de cocina, caja y mesero cuando el estado de un pedido cambie.

RF06: El sistema debe aplicar reglas de promoción automáticas según la hora del día o el día de la semana.

RF07: El sistema debe realizar el cierre de caja generando un reporte detallado de las ventas totales y por categoría.

##Requisitos No Funcionales (RNF)

    RNF01 (Portabilidad y Ejecución): El software debe compilar y ejecutarse correctamente en entornos Linux (Debian 13) utilizando Java 21.

    RNF02 (Arquitectura Limpia): La lógica de negocio debe estar completamente desacoplada de las interfaces de usuario (consola y GUI), permitiendo que ambas compartan el mismo núcleo.

    RNF03 (Patrones de Diseño): El sistema debe implementar al menos tres patrones de diseño pertenecientes a categorías distintas (creacionales, estructurales y de comportamiento).

    RNF04 (Mantenibilidad): El código fuente debe estar estructurado en paquetes con responsabilidades claras y documentado mediante comentarios y diagramas UML coherentes.
