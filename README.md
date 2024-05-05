# orquestador


### Jib Plugin

Generar la imagen docker sin el archivo Dockerfile.

Primero, realizar la siguiente modificación en el archivo settings.xml de maven.

Agregar:

* username, usuario de docker hub, omitir el correo.

```xml
<server>
  <id>registry.hub.docker.com</id>
  <username>[username]</username>
  <password>[password]</password>
</server>
```

Ejecución

```
mvn clean package -P prod
```
