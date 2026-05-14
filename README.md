# ds-project

## Descripción

Este repositorio es el proyecto final de la materia de Estructura de Datos.

## Git - Flujo de trabajo y convenciones

1. Usar convenciones de nomenclatura para los commits, por ejemplo:
   - `feat: agregar nueva funcionalidad`
   - `fix: corregir bug`
   - `docs: actualizar documentación`
   - `refactor: refactorizar código`
   - `test: agregar o modificar pruebas`
   - `chore: tareas de mantenimiento`
   - `style: cambios de formato sin afectar la lógica`
2. Moverse a la rama `dev` para trabajar en nuevas funcionalidades o correcciones.
   - `git checkout dev`
   - `git pull origin dev` para asegurarse de tener la última versión.
3. Empezar a trabajar en la nueva funcionalidad o corrección.
4. Agregar los cambios al área de staging:
   - `git add .`
5. Hacer un commit con un mensaje descriptivo siguiendo las convenciones de nomenclatura:
   - `git commit -m "feat: add new feature"`
6. Subir los cambios a la rama `dev`:
   - `git pull origin dev` para asegurarse de que no haya conflictos con otros cambios.
   - Resolver cualquier conflicto si es necesario.
   - `git push origin dev`

Nota: No se deben hacer commits directamente en la rama `main`. Todos los cambios deben pasar por la rama `dev` para asegurar un flujo de trabajo organizado y controlado.

## Arquitectura del Core

El núcleo del sistema se encuentra en `src/main/java/com/ds/core/` y está organizado de forma modular. Cada submódulo tiene una responsabilidad específica y está desacoplado del resto, facilitando el mantenimiento y la escalabilidad.

```
core/
│
├── models/        → Entidades del dominio
├── graph/         → Estructura de grafos
├── algorithms/    → Algoritmos de búsqueda y optimización
├── services/      → Lógica de negocio
├── priority/      → Sistema de prioridades
├── simulation/    → Simulación de escenarios
├── managers/      → Coordinación global del sistema
├── validators/    → Validación de datos
├── utils/         → Herramientas auxiliares
└── enums/         → Enumeraciones del sistema
```

---

### models/ — Entidades del dominio

Representan los objetos principales del sistema.

| Archivo               | Responsabilidad                                                                   |
| --------------------- | --------------------------------------------------------------------------------- |
| `Order.java`          | Pedido del sistema. Controla estado, prioridad, tiempos, cliente y ruta asociada. |
| `Client.java`         | Cliente con información de contacto y ubicación (nodo en el grafo).               |
| `Route.java`          | Ruta calculada: nodos recorridos, distancia total y tiempo estimado.              |
| `Node.java`           | Vértice del grafo. Puede representar un cliente, restaurante o intersección.      |
| `Edge.java`           | Arista del grafo con distancia, tiempo de recorrido y estado de la ruta.          |
| `DeliveryDriver.java` | Repartidor con disponibilidad, ubicación actual y pedido asignado.                |
| `DriverStatus.java`   | Enum con estados del repartidor: `AVAILABLE`, `BUSY`, `OFFLINE`.                  |
| `OrderStatus.java`    | Enum con estados del pedido: `PENDING`, `IN_PROGRESS`, `DELIVERED`, `CANCELLED`.  |

---

### graph/ — Estructura de grafos

Representación y manipulación del grafo de rutas.

| Archivo              | Responsabilidad                                                                      |
| -------------------- | ------------------------------------------------------------------------------------ |
| `Graph.java`         | Clase principal del grafo. Almacena nodos, gestiona conexiones y agrega rutas.       |
| `AdjacencyList.java` | Implementación interna con listas de adyacencia para optimizar memoria y búsquedas.  |
| `GraphBuilder.java`  | Constructor del grafo. Inicializa mapas y crea la estructura desde datos de entrada. |
| `GraphManager.java`  | Administrador del grafo. Actualiza tráfico, modifica rutas y gestiona bloqueos.      |

---

### algorithms/ — Algoritmos de búsqueda y optimización

Algoritmos clásicos sobre grafos aplicados a la logística.

| Archivo                  | Responsabilidad                                                                       |
| ------------------------ | ------------------------------------------------------------------------------------- |
| `DijkstraAlgorithm.java` | Calcula la ruta más corta o rápida entre dos nodos (camino óptimo).                   |
| `BFSAlgorithm.java`      | Recorrido por amplitud. Útil para buscar clientes cercanos y explorar zonas.          |
| `DFSAlgorithm.java`      | Recorrido en profundidad. Usado para simulación de rutas y exploración de conexiones. |
| `RouteOptimizer.java`    | Coordina los algoritmos y selecciona la mejor ruta según criterios del sistema.       |

---

### services/ — Lógica de negocio

Orquesta las operaciones del sistema exponiendo una capa de servicio.

| Archivo                     | Responsabilidad                                                                    |
| --------------------------- | ---------------------------------------------------------------------------------- |
| `OrderService.java`         | Gestión de pedidos: creación, actualización de estado, asignación de repartidores. |
| `RouteService.java`         | Cálculo y consulta de rutas mediante el grafo y los algoritmos.                    |
| `ClientService.java`        | Gestión de clientes: registro, búsqueda y actualización de datos.                  |
| `DeliveryService.java`      | Coordinación de entregas: asigna repartidores y sigue el progreso.                 |
| `EstimatedTimeService.java` | Estimación de tiempos de entrega basada en distancia, tráfico y preparación.       |

---

### priority/ — Sistema de prioridades

Clasifica automáticamente los pedidos según factores definidos.

| Archivo                   | Responsabilidad                                                                                |
| ------------------------- | ---------------------------------------------------------------------------------------------- |
| `PriorityCalculator.java` | Calcula la prioridad de un pedido usando distancia, tiempo de preparación y tráfico.           |
| `PriorityRules.java`      | Define las reglas y umbrales del sistema de prioridades.                                       |
| `PriorityManager.java`    | Gestiona las prioridades activas y reordena la cola de pedidos cuando cambian las condiciones. |

---

### simulation/ — Simulación de escenarios

Genera eventos simulados para probar el comportamiento del sistema.

| Archivo                  | Responsabilidad                                                     |
| ------------------------ | ------------------------------------------------------------------- |
| `TrafficSimulator.java`  | Simula condiciones de tráfico que afectan los tiempos de las rutas. |
| `DeliverySimulator.java` | Simula el flujo completo de entregas para pruebas y validación.     |
| `EventGenerator.java`    | Genera eventos aleatorios: accidentes, retrasos, bloqueos en rutas. |

---

### managers/ — Coordinación global del sistema

Controladores que integran y orquestan todos los módulos.

| Archivo               | Responsabilidad                                                                 |
| --------------------- | ------------------------------------------------------------------------------- |
| `SystemManager.java`  | Coordina todos los módulos del core. Punto de entrada de la lógica del sistema. |
| `OrderManager.java`   | Administra los pedidos activos: cola de prioridad, asignación y seguimiento.    |
| `SessionManager.java` | Gestiona el estado global de la sesión y la configuración del sistema.          |

---

### validators/ — Validación de datos

Garantiza la integridad de los datos antes de procesarlos.

| Archivo                | Responsabilidad                                                              |
| ---------------------- | ---------------------------------------------------------------------------- |
| `OrderValidator.java`  | Valida datos de pedidos: campos obligatorios, estados válidos, consistencia. |
| `RouteValidator.java`  | Valida rutas: nodos existentes, conexiones válidas, distancias positivas.    |
| `ClientValidator.java` | Valida datos de clientes: formato de contacto, dirección, ubicación.         |

---

### utils/ — Herramientas auxiliares

Funciones de uso general reutilizables en todo el sistema.

| Archivo                   | Responsabilidad                                                                  |
| ------------------------- | -------------------------------------------------------------------------------- |
| `DistanceCalculator.java` | Cálculo de distancias entre nodos (Euclidiana, Manhattan, etc.).                 |
| `TimeFormatter.java`      | Formateo y conversión de unidades de tiempo.                                     |
| `IdGenerator.java`        | Generación de identificadores únicos para pedidos, clientes y rutas.             |
| `Constants.java`          | Constantes globales del sistema: valores por defecto, umbrales, configuraciones. |

---

### enums/ — Enumeraciones del sistema

Tipos enumerados utilizados transversalmente.

| Archivo              | Responsabilidad                                                                     |
| -------------------- | ----------------------------------------------------------------------------------- |
| `PriorityLevel.java` | Niveles de prioridad: `LOW`, `MEDIUM`, `HIGH`, `URGENT`.                            |
| `RouteStatus.java`   | Estado de una ruta: `OPEN`, `CONGESTED`, `BLOCKED`.                                 |
| `NodeType.java`      | Tipo de nodo en el grafo: `CLIENT`, `RESTAURANT`, `INTERSECTION`, `DELIVERY_POINT`. |

---

### Flujo interno del sistema

```
JavaFX UI
    ↓
Controllers  (com.ds.controllers)
    ↓
Services  (core/services)
    ↓
Managers  (core/managers)
    ↓
Algorithms + Graph  (core/algorithms + core/graph)
    ↓
Models  (core/models)
```

Las peticiones de la UI pasan por los controladores, que delegan en los servicios. Los servicios usan los managers para coordinar la lógica, los managers invocan los algoritmos sobre el grafo, y todo opera sobre los modelos del dominio.

## Información importante del proyecto final
