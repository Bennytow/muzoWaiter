# Estilo arquitectónico de muzoWaiter

## Arquitectura seleccionada

Arquitectura monolítica organizada en capas.

## Definición

Una arquitectura monolítica integra los componentes
del sistema en una única unidad de compilación
y despliegue.

muzoWaiter se distribuye mediante un archivo JAR
que contiene las interfaces, los servicios,
las entidades y los patrones de diseño.

## Organización interna

- Presentación: consola y Swing.
- Servicios: pedidos, promociones, caja y reportes.
- Dominio: productos, pedidos, combos y pagos.
- Patrones: componentes reutilizables de diseño.

## Justificación

Se seleccionó este estilo por la naturaleza del
proyecto, su alcance académico y la ausencia de
requisitos de infraestructura distribuida.

## Ventajas

- Despliegue sencillo.
- Comunicación directa entre componentes.
- Menor complejidad de infraestructura.
- Facilidad de compilación y pruebas.
- Reutilización de la lógica de negocio.

## Desventajas

- Escalabilidad independiente limitada.
- Los componentes se despliegan conjuntamente.
- Un crecimiento excesivo puede dificultar
  el mantenimiento.
- No existe sincronización entre procesos.

## Diferencia frente a microservicios

muzoWaiter no divide sus funcionalidades en
servicios desplegables independientemente.

## Diferencia frente a arquitectura hexagonal

Aunque existe separación de responsabilidades,
no se implementa formalmente una arquitectura
de puertos y adaptadores.

## Conclusión

muzoWaiter implementa una arquitectura monolítica
con separación interna en capas, apoyada por
cinco patrones de diseño: Decorator, Observer,
Strategy, Builder y Singleton.
