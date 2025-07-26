# Projet d'association - Spring Boot

## Description
Projet **Spring Boot + PostgreSQL** sous Docker.  
Objectif : tester un contrôleur REST qui renvoie les employés stockés dans la base `api_asso`.

## Prérequis
- Docker / Docker Compose
- Java 21
- Maven
- Postgre 16

## Lancer le projet
docker compose down -v # reset
docker compose build --no-cache
docker compose up -d

## Vérifier PostgreSQL
docker exec -it spring_postgres psql -U postgres -d api_asso
\dt
SELECT * FROM employees;

## Tester le serveur Tomcat
docker logs spring_app | grep "Tomcat started"
curl http://localhost:8080/

## Tester l’API
curl http://localhost:8080/
# assocationprojetspringboot
projet springBoot
