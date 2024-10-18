# Usa una imagen base de OpenJDK
FROM eclipse-temurin:17-jdk-alpine AS build

# Establece el directorio de trabajo
WORKDIR /app

# Instala bash
RUN apk add --no-cache bash

# Copia el archivo de Gradle y las dependencias
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src
COPY gradlew .  # Copia el script gradlew

# Agrega permisos de ejecución al script gradlew
RUN chmod +x gradlew

# Construye el proyecto usando Gradle
RUN ./gradlew build --no-daemon

# Crea una imagen más pequeña para el contenedor de producción
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
