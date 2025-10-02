# Build (Java 17)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package spring-boot:repackage

# Runtime (JRE 17)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV SERVER_PORT=8081
# Si el back corre en tu Windows en :8080
ENV BACKEND_BASE_URL=http://host.docker.internal:8080
ENV JAVA_OPTS=""
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar --server.port=${SERVER_PORT} --backend.base-url=${BACKEND_BASE_URL}"]
