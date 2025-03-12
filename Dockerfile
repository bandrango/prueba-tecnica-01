# ===== STAGE 1: Build the application =====
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN apk add --no-cache maven
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn package -DskipTests

# ===== STAGE 2: Run the application =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR generado en la fase anterior
COPY --from=build /app/target/*.jar sample.jar

# Exponemos el puerto 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sample.jar"]