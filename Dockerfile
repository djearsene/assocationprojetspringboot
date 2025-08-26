# Étape 1 : builder le jar avec Maven (si tu n’as pas déjà un jar prêt)
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : exécuter le jar dans un runtime léger
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Copier le jar compilé depuis l’étape précédente
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
