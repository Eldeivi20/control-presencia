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
