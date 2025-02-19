FROM bellsoft/liberica-openjdk-debian:21
WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/application.properties application.properties
COPY .env .env
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application.properties"]