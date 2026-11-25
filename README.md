# control-presencia

Sistema de control de presencia para registrar fichajes, definir horarios y consultar el cumplimiento horario de personas. Proyecto extraordinario de Ingeniería del Software II (EII, ULPGC).

## Objetivo de la entrega

Construir un backend y tres aplicaciones frontend diferenciadas que permitan:

- Registrar entradas y salidas de una persona.
- Definir horarios de distintos tipos y asignarlos a personas durante un periodo.
- Evaluar los fichajes contra el horario asignado y consultar el estado resultante: presencia actual, ausencias, retrasos, salidas tempranas, horas trabajadas e incidencias.

El sistema soporta como mínimo tres familias de horario:

| Tipo | Ejemplo |
|---|---|
| Horario corrido | Oficina de 08:00 a 15:00 |
| Horario partido | Restaurante de 10:00 a 14:00 y de 17:00 a 21:00 |
| Turnos y guardias | Hospital con turnos rotatorios de mañana, tarde y noche, y guardias de 24 horas |

## Decisiones tecnológicas

| Aspecto | Elección |
|---|---|
| Lenguaje | Java, compilado con `release 25` |
| JDK | 25 o superior (desarrollado con OpenJDK 27) |
| Construcción | Maven multimódulo |
| Backend HTTP | Javalin |
| JSON | Gson |
| Persistencia | SQLite mediante JDBC |
| Frontends | Aplicaciones de escritorio Swing |
| Pruebas | JUnit 5 |

## Estructura de la entrega

| Módulo | Responsabilidad |
|---|---|
| `presence-backend` | Backend ejecutable de forma independiente con arquitectura hexagonal |
| `presence-client` | Adaptador de comunicación HTTP sustituible compartido por los frontends |
| `attendance-app` | Frontend de registro de asistencia |
| `presence-query-app` | Frontend de consulta de presencia e incidencias |
| `schedule-admin-app` | Frontend de definición y asignación de horarios |

## Modelo de dominio

El dominio vive en `presence-backend`, en el paquete `software.ulpgc.presence.domain`, y no depende de ninguna librería externa.

| Concepto | Clase | Descripción |
|---|---|---|
| Persona | `Employee`, `EmployeeId` | Persona de prueba identificada por un código, con un nombre visible y su centro de trabajo |
| Tramo horario | `TimeSlot` | Hora de inicio y duración. Permite tramos que cruzan la medianoche y guardias de 24 horas |
| Intervalo concreto | `TimeInterval` | Tramo situado en una fecha. Calcula duración, pertenencia y solapamiento con otro intervalo |
| Periodo | `DateRange` | Rango de fechas con ambos extremos incluidos |
| Asignación | `ScheduleAssignment` | Horario (`ScheduleId`) asignado a una persona durante un periodo |
| Fichaje | `ClockEvent` | Entrada o salida (`ClockEventType`) con fecha, hora, persona y origen (`ClockEventOrigin`) |
| Incidencia | `Incident`, `IncidentType` | Retraso, salida temprana, ausencia, salida pendiente o trabajo fuera de horario, con su desviación |

Las reglas de validación se aplican en el propio constructor de cada objeto, de modo que un objeto de dominio nunca existe en un estado inválido. Los errores se expresan con excepciones que heredan de `DomainException`:

| Excepción | Cuándo se produce |
|---|---|
| `MissingRequiredValueException` | Falta un dato obligatorio o está en blanco |
| `InvalidTimeSlotException` | El tramo dura cero horas o más de 24 horas |
| `InvalidTimeIntervalException` | El intervalo no empieza antes de terminar |
| `InvalidDateRangeException` | El periodo termina antes de empezar |
| `NegativeDeviationException` | La desviación de una incidencia es negativa |

## Cómo compilar

```
mvn clean verify
```

## Plan de desarrollo

El proyecto se construye de forma progresiva. Cada fase se desarrolla en su propia rama y se integra en `main` mediante un merge sin fast-forward, de modo que el historial muestre el avance real.

| Fase | Rama | Contenido |
|---|---|---|
| 1 | `feature/project-setup` | Repositorio, `.gitignore`, pom padre y README inicial |
| 2 | `feature/domain-model` | Persona, tramo horario, asignación, fichaje e incidencia |
| 3 | `feature/schedule-rules` | Horario corrido, partido, turnos rotatorios y guardias |
| 4 | `feature/presence-evaluation` | Cálculo de estado diario, retrasos, horas trabajadas y desviación |
| 5 | `feature/use-cases` | Casos de uso, puertos de entrada y salida y errores de dominio |
| 6 | `feature/sqlite-persistence` | Adaptadores SQLite, datos iniciales y registro de auditoría |
| 7 | `feature/http-api` | API REST con Javalin y contrato documentado |
| 8 | `feature/presence-client` | Adaptador HTTP sustituible para los frontends |
| 9 | `feature/attendance-app` | Frontend de fichaje |
| 10 | `feature/presence-query-app` | Frontend de consulta |
| 11 | `feature/schedule-admin-app` | Frontend de administración de horarios |
| 12 | `feature/holidays-and-justifications` | Idea propia: calendario de festivos e incidencias justificadas |
| 13 | `docs/final-readme` | Capturas, verificación y límites conocidos |

## Convenciones

- El código no contiene comentarios: clases, métodos y variables deben explicarse por su nombre.
- El dominio no depende de Javalin, Gson, JDBC ni Swing.
- No se suben datos personales reales, credenciales, bases de datos ni artefactos de compilación.
