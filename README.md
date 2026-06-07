# Reto 1 - Automatización de Servicios con Serenity BDD, Cucumber y Screenplay

Proyecto de automatización de pruebas para la API de **ReqRes** utilizando **Screenplay Pattern**, **Serenity BDD**, **Cucumber**, **JUnit 5** y **REST Assured / Serenity Screenplay REST**.

---

## Descripción del Proyecto

Este proyecto implementa pruebas automatizadas para validar operaciones REST sobre la API pública de **ReqRes**. La solución cubre escenarios exitosos (happy path), escenarios negativos y validaciones sobre respuestas HTTP y contenido JSON.

El proyecto fue construido con un enfoque orientado a mantenibilidad, reutilización y legibilidad, usando el patrón **Screenplay**, clases de interacción para cada endpoint, preguntas (**Questions**) para consultar el estado del sistema, y reportes generados con **Serenity BDD**.

### Escenarios cubiertos

1. **Listar usuarios**
2. **Registrar usuario exitosamente**
3. **Registrar usuario sin contraseña**
4. **Actualizar un usuario existente**
5. **Eliminar un usuario existente**

---

## Patrón de Diseño: Screenplay Pattern

El proyecto utiliza el **Screenplay Pattern**, un patrón de automatización que modela las pruebas desde la perspectiva de un **Actor** que ejecuta acciones sobre el sistema y luego responde preguntas sobre el resultado.

### Conceptos clave aplicados en este proyecto

```text
Actor (Analista API)
   │
   ├── Interactions (Acciones sobre la API)
   │   ├── GetUsers
   │   ├── GetSingleUser
   │   ├── RegisterUser
   │   ├── RegisterUserWithoutPassword
   │   ├── UpdateUser
   │   └── DeleteUser
   │
   ├── Questions (Consultas sobre la respuesta)
   │   ├── ResponseStatusCode
   │   ├── UsersCount
   │   ├── JsonFieldValue
   │   ├── JsonStringFieldValue
   │   └── ResponseBodyIsEmpty
   │
   └── Context
       └── ApiResponseContext
```

### Ejemplo de código Screenplay

```java
actor.attemptsTo(GetUsers.fromPage(2));
guardarUltimaRespuesta();

actor.should(
    seeThat("el código de respuesta",
        ResponseStatusCode.from(apiResponseContext),
        equalTo(200)),
    seeThat("la cantidad de usuarios en la respuesta",
        UsersCount.from(apiResponseContext),
        greaterThan(0))
);
```

### Ventajas del patrón Screenplay

**Alta expresividad**: el flujo de prueba se lee de forma natural  
**Mantenibilidad**: cada endpoint está aislado en clases de interacción  
**Reutilización**: Questions e Interactions se reutilizan entre escenarios  
**Escalabilidad**: es sencillo agregar nuevos endpoints, preguntas o escenarios  
**Separación de responsabilidades**: request, respuesta, steps y validaciones están distribuidos por capas  
**Mejor reporting**: Serenity permite visualizar resultados y cobertura funcional de manera clara  

---

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|---|---:|---|
| **Java** | 18 | Lenguaje de programación |
| **Gradle** | Wrapper / ejecución observada 9.3.0 | Gestión del build y dependencias |
| **Serenity BDD** | 5.3.7 | Framework de automatización y reportes |
| **Cucumber** | 7.34.2 | BDD con Gherkin |
| **JUnit Platform Suite** | 6.0.3 | Ejecución de la suite |
| **Serenity Screenplay REST** | 5.3.7 | Integración Screenplay para APIs REST |
| **REST Assured** | Incluido vía Serenity REST | Consumo y validación de servicios REST |
| **IntelliJ IDEA** | 2026.1.x | IDE de desarrollo |
| **Postman** | N/A | Mapeo y validación manual inicial |

---

## Estructura del Proyecto

```text
reto1-api-serenity/
├── src/
│   └── test/
│       ├── java/com/cun/reto1/
│       │   ├── context/
│       │   │   └── ApiResponseContext.java
│       │   ├── interactions/
│       │   │   ├── GetUsers.java
│       │   │   ├── GetSingleUser.java
│       │   │   ├── RegisterUser.java
│       │   │   ├── RegisterUserWithoutPassword.java
│       │   │   ├── UpdateUser.java
│       │   │   └── DeleteUser.java
│       │   ├── models/
│       │   │   ├── RegisterUserRequest.java
│       │   │   ├── RegisterUserWithoutPasswordRequest.java
│       │   │   └── UpdateUserRequest.java
│       │   ├── questions/
│       │   │   ├── UsersCount.java
│       │   │   ├── ResponseStatusCode.java
│       │   │   ├── JsonFieldValue.java
│       │   │   ├── JsonStringFieldValue.java
│       │   │   └── ResponseBodyIsEmpty.java
│       │   ├── runners/
│       │   │   └── CucumberTestSuite.java
│       │   ├── stepdefinitions/
│       │   │   └── UsuariosReqresStepDefinitions.java
│       │   └── utils/
│       │       └── ApiConfig.java
│       └── resources/
│           └── features/
│               └── usuarios_reqres.feature
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

---

## Requisitos Previos

- **Java 17 o superior**
- **Gradle** (o uso del wrapper del proyecto)
- **IntelliJ IDEA** u otro IDE con soporte Java
- **Postman** para validación manual inicial
- Conexión a Internet
- **API key válida de ReqRes**

---

##  Configuración de la API

La configuración base del proyecto está centralizada en:

```text
src/test/java/com/cun/reto1/utils/ApiConfig.java
```

Ejemplo:

```java
public class ApiConfig {

    public static final String BASE_URL = "https://reqres.in/api";
    public static final String API_KEY = "TU_API_KEY_AQUI";

    private ApiConfig() {
        // Evitar instanciación
    }
}
```
---

## Instalación y Ejecución

### 1. Clonar o ubicar el proyecto

```bash
cd C:\Automation\Reto1\reto1-api-serenity
```

### 2. Validar dependencias / compilar

```bash
.\gradlew.bat clean testClasses
```

### 3. Ejecutar la suite completa

```bash
.\gradlew.bat clean test
```

### 4. Ejecutar y generar reportes Serenity

```bash
.\gradlew.bat clean test aggregate
```

---

## Runner de pruebas

La ejecución de los escenarios se realiza a través de:

```text
CucumberTestSuite.java
```

Este runner integra:

- **Cucumber**
- **JUnit 5**
- **Serenity Reporter**
- configuración del **glue** para las Step Definitions

---

## Escenarios Automatizados

### 1. Listar usuarios

**Objetivo:** validar que el servicio responda exitosamente y que contenga usuarios.

- Endpoint: `GET /users?page=2`
- Validaciones principales:
  - HTTP `200`
  - lista `data` con al menos un usuario

---

### 2. Registrar usuario exitosamente

**Objetivo:** validar un registro exitoso con credenciales válidas.

- Endpoint: `POST /register`
- Body esperado:

```json
{
  "email": "eve.holt@reqres.in",
  "password": "pistol"
}
```

- Validaciones principales:
  - HTTP `200`
  - presencia de `id`
  - presencia de `token`

---

### 3. Registrar usuario sin contraseña

**Objetivo:** validar el comportamiento negativo cuando falta un dato obligatorio.

- Endpoint: `POST /register`
- Body esperado:

```json
{
  "email": "sydney@fife"
}
```

- Validaciones principales:
  - HTTP `400`
  - campo `error`
  - mensaje `Missing password`

---

### 4. Actualizar un usuario existente

**Objetivo:** validar la actualización de datos de un usuario existente.

- Endpoint: `PUT /users/2`
- Body esperado:

```json
{
  "name": "morpheus",
  "job": "zion resident"
}
```

- Validaciones principales:
  - HTTP `200`
  - `name = morpheus`
  - `job = zion resident`
  - presencia de `updatedAt`

---

### 5. Eliminar un usuario existente

**Objetivo:** validar una eliminación exitosa.

- Endpoint: `DELETE /users/2`
- Validaciones principales:
  - HTTP `204`
  - body vacío

---

## Mapeo inicial en Postman

Antes de la automatización, se realizó el mapeo manual de los endpoints en **Postman** para:

- validar manualmente cada escenario
- confirmar los códigos HTTP esperados
- identificar la necesidad del header `x-api-key`
- exportar la colección como evidencia del reto

La colección Postman exportada hace parte de los entregables del proyecto.

---

## Reportes

### Reporte Serenity (principal)

Después de ejecutar:

```bash
.\gradlew.bat clean test aggregate
```

el reporte agregado de Serenity se genera en una ruta similar a:

```text
target/site/serenity/index.html
```

En el reporte se puede consultar:

- **Overall Test Results**
- **Requirements**
- **Features**
- **Feature Coverage By Scenario**
- **Key Statistics**
- detalle de ejecución por feature y escenario

### Reporte de pruebas de Gradle

Adicionalmente, Gradle genera un reporte HTML básico de resultados de test en una ruta similar a:

```text
build/reports/tests/test/index.html
```

> Este reporte sirve como evidencia de ejecución, aunque el reporte principal recomendado para la entrega es el de **Serenity BDD**.

---

##  Resultado alcanzado

Durante el desarrollo del proyecto se logró:

- Configuración completa del entorno de automatización
- Mapeo de endpoints en Postman
- Automatización de los 5 escenarios solicitados
- Implementación con enfoque Screenplay
- Validaciones con `seeThat(...)`
- Uso de `Question` para consultar el estado del sistema
- Validaciones del contenido JSON con `JsonPath`
- Ejecución exitosa de la suite y generación de reportes Serenity

---

## Arquitectura aplicada

### 1. Context

```java
ApiResponseContext
```

Permite almacenar:
- `statusCode`
- `responseBody`

y compartir la respuesta entre `When` y `Then`.

### 2. Interactions

Encapsulan la llamada REST concreta a cada endpoint.

Ejemplo:

```java
actor.attemptsTo(GetUsers.fromPage(2));
actor.attemptsTo(RegisterUser.withValidCredentials());
actor.attemptsTo(UpdateUser.withIdAndValidData(2));
```

### 3. Questions

Permiten consultar la respuesta del servicio de forma reusable.

Ejemplo:

```java
ResponseStatusCode.from(apiResponseContext)
UsersCount.from(apiResponseContext)
JsonFieldValue.from(apiResponseContext, "id")
JsonStringFieldValue.from(apiResponseContext, "token")
```

### 4. Step Definitions

La clase `UsuariosReqresStepDefinitions` orquesta:
- Creación del actor
- Ejecución de interacciones
- Almacenamiento de respuesta
- Validación con `seeThat(...)`

---

## Ejemplo de validación Screenplay

```java
actor.should(
        seeThat("el código de respuesta del registro",
                ResponseStatusCode.from(apiResponseContext),
                equalTo(200)),
        seeThat("el token generado en el registro",
                JsonStringFieldValue.from(apiResponseContext, "token"),
                not(emptyOrNullString()))
);
```

---

## Troubleshooting

### 1. Error `missing_api_key`

Verificar que la `API_KEY` configurada en `ApiConfig.java` sea válida y esté siendo enviada en el header `x-api-key`.

### 2. Problemas SSL / `PKIX path building failed`

En algunos momentos del desarrollo se identificaron restricciones/intermitencias del entorno de red corporativa que afectaron el handshake SSL de Java. Una vez estabilizado el entorno, la suite pudo ejecutarse correctamente.

### 3. Warnings de SLF4J

Mensajes como:

```text
SLF4J(W): No SLF4J providers were found.
```

no impidieron la ejecución exitosa del proyecto.

### 4. Warning sobre selector `features`

La advertencia sobre:

```text
The classpath resource selector 'features' should not be used...
```

tampoco impidió la generación correcta de la suite y los reportes.

---

## Buenas prácticas aplicadas

- Separación de responsabilidades por packages
- Centralización de configuración en `ApiConfig`
- Uso de `ApiResponseContext` para compartir respuestas
- Validación desacoplada mediante `Questions`
- Uso de `seeThat(...)` en los `Then`
- Validación de contenido JSON con `JsonPath`
- Generación de reportes con Serenity BDD

---

## Mejoras futuras

Como posibles mejoras del proyecto, se podrían considerar:

- Externalizar la API key a variables de entorno
- Reducir repetición en Step Definitions con más métodos utilitarios
- Mejorar aún más la abstracción de actores y tareas
- Parametrizar datos de prueba
- Integrar el proyecto con un pipeline CI/CD

---

## Autor

**Einar Andres Escobar Gallo**  
**Reto 1 - Automatización de Servicios API**  
**BDG**

---

## Entregables relacionados

Este proyecto se acompaña de los siguientes entregables:

- Colección Postman exportada
- Código fuente del proyecto
- Reportes Serenity generados
- README del proyecto

---

## Observación final

Este desarrollo fue construido como parte de un reto técnico de automatización, buscando no solo cumplir funcionalmente con los escenarios solicitados, sino también aplicar buenas prácticas de diseño, organización y legibilidad utilizando el patrón **Screenplay**.
