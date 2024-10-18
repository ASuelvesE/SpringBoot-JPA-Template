# Usa una imagen base de OpenJDK
FROM openjdk:17.0-buster

# Define el puerto del servidor como un argumento
ARG SERVER_PORT=8080

# Define el nombre del archivo JAR y el directorio de la aplicación
ENV APP_FILE=api-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app

# Crea el directorio de la aplicación
RUN mkdir $APP_HOME

# Expone el puerto en el que la aplicación escucha
EXPOSE $SERVER_PORT

# Copia el archivo JAR construido al contenedor
COPY ./build/libs/$APP_FILE $APP_HOME/$APP_FILE

# Establece el directorio de trabajo
WORKDIR $APP_HOME

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "exec java -jar $APP_FILE"]
