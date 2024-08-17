# Orchestrator Service

## Descripción

Este proyecto es un orquestador desarrollado en Java utilizando el framework Spring Boot. Se encarga de orquestar
llamadas a otros servicios mediante WebClient y Kafka, y se gestiona con Maven para la administración de dependencias y
la construcción del proyecto.

## Tecnologías Utilizadas

### Lenguaje de Programación

- **Java 17**: El lenguaje principal utilizado para el desarrollo del proyecto.

### Frameworks

- **Spring Boot**: Framework para la creación de aplicaciones basadas en Spring que simplifica la configuración y el
  desarrollo de aplicaciones nuevas.
- **Spring WebFlux**: Utilizado para construir aplicaciones reactivas y no bloqueantes.
- **Spring Data MongoDB Reactive**: Proporciona soporte para trabajar con MongoDB de manera reactiva.
- **Spring Kafka**: Utilizado para la integración con Apache Kafka.

### Gestión de Dependencias

- **Maven**: Herramienta de gestión de proyectos y comprensión utilizada para la construcción del proyecto, la gestión
  de dependencias y la documentación del proyecto.

### Librerías Adicionales

- **Lombok**: Utilizado para reducir el código boilerplate mediante anotaciones.
- **MapStruct**: Utilizado para la generación automática de mapeos entre objetos.
- **OWASP Encoder**: Utilizado para la codificación segura de datos.
- **Validation API**: Proporciona anotaciones para la validación de datos.

### Testing

- **Spring Boot Starter Test**: Proporciona soporte para pruebas unitarias y de integración.
- **Reactor Test**: Utilizado para pruebas reactivas.
- **MockWebServer**: Utilizado para pruebas de servidores HTTP simulados.
- **JaCoCo**: Utilizado para la cobertura de código.

## Estructura del Proyecto

El proyecto sigue una estructura estándar de Spring Boot con Maven y está organizado utilizando la arquitectura
hexagonal (puertos y adaptadores):

 ```
src/ 
├── main/ 
│ ├── java/ 
│ │ └── com/ 
│ │ └── entrevistador/ 
│ │ └── orquestador/ 
│ │ ├── application/ # Capa de aplicación 
│ │ │ ├── service/ # Servicios de aplicación 
│ │ │ └── usecases/ # Casos de usos de aplicación
│ │ ├── dominio/ # Capa de dominio 
│ │ │ ├── excepciones/ # Excepciones de dominio 
│ │ │ ├── model/ # Modelos de dominio 
│ │ │ ├── port/ # (puertos) 
│ │ │ └── service/ # Servicios de dominio 
│ │ ├── infrastructure/ # Capa de infraestructura 
│ │ │ ├── adapter/ # Adaptadores (implementaciones de puertos) 
│ │ │ ├── beanconfiguration/ # Configuraciones de Spring 
│ │ │ ├── properties/ # Propiedades de la aplicación
│ │ │ └── rest/ # Enpoints REST HTTP 
│ ├── resources/ 
│ │ └── application.properties 
 ```

## Requisitos Previos

- **Java 17** o superior
- **Maven 3.6.3** o superior
- **Docker** (para ejecutar MongoDB en un contenedor)

# Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/EntrevistadorInteligente/si-orchestrator.git
    ```
2. Navega al directorio del proyecto:
    ```sh
    cd si-orchestrator
    ```
3. Instala las dependencias:
    ```sh
    mvn clean install
    ```

## Ejecución del Proyecto

Para ejecutar el proyecto, utilice el siguiente comando de Maven:

```sh
mvn spring-boot:run
```

## Compilación del Proyecto

Para compilar el proyecto, utilice el siguiente comando de Maven:

```sh
mvn clean install
```

## Entorno local (Docker Compose)

Para desarrollo en entorno local el perfil por defecto es "local" o también se puede aputar a un perfil especifico desde
el application.properties en 'spring.profiles.active', reemplazando el valor por el perfil especifico "local", "dev", "
prod". También se debe ejecutar el siguiente docker-compose "docker-compose-local.yml"

```sh
docker compose up -d
```

## Contribución
1. Clonar el repositorio
2. Crea una nueva rama desde la tarea con el nombre (feature/itemIdQuery|hotfix/itemIdQuery).
3. Realiza tus cambios y haz commit (git commit -m 'Agrega nueva funcionalidad').
4. Sube tus cambios (git push origin feature/itemIdQuery).
5. Abre un Pull Request.

## Derechos de Autor

\© 2024 Entrevistador Inteligente.

Este proyecto es de código abierto y se distribuye bajo la licencia GNU AFFERO GENERAL PUBLIC. Para más detalles,
consulte el archivo `LICENSE` en el repositorio.
