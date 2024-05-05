FROM amazoncorretto:17-alpine3.18-jdk

EXPOSE 8081

RUN mkdir -p /app/

COPY target/orquestador-0.0.1-SNAPSHOT.jar /app/orquestador.jar

ENTRYPOINT ["java", "-jar", "/app/orquestador.jar"]
