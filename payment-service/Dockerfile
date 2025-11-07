# ================================
# 🧱 Etapa 1: Build con Maven
# ================================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de Maven (pom.xml y código fuente)
COPY pom.xml .
COPY settings-github.xml ./secrets/settings-github.xml
# Descargar dependencias antes de copiar todo (mejora cacheo)
RUN mvn dependency:go-offline -s ./secrets/settings-github.xml


# Ahora copiamos el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests

# ================================
# 🚀 Etapa 2: Imagen final (solo runtime)
# ================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar el jar desde la imagen anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto que expone la aplicación (si es Spring Boot, usualmente 8080)
EXPOSE 8083

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
