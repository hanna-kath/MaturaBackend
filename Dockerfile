# Basisimage ist openjdk in der Version 17
FROM openjdk:17

# Verzeichnis mit Projektdaten
WORKDIR /app

# Daten in Container importieren
COPY target/spengerspital-0.0.1-SNAPSHOT.jar spengerspital.jar

# Server starten
CMD ["java", "-jar", "spengerspital.jar", "--spring.datasource.url=jdbc:mariadb://localhost:33306/mis"]
