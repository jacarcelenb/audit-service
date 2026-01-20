FROM eclipse-termurin:21.0.9_10-jdk AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskpiTests

FROM eclipse-termurin:21.0.9_10-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
