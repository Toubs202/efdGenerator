# Verwende ein JDK-Image anstelle des JRE-Images
FROM openjdk:11-jdk-slim

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere alle Java-Quellcode-Dateien in das Arbeitsverzeichnis
COPY . /app

# Installiere netcat (nc) für Verbindungstests
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

# Kopiere die JAR-Dateien für Abhängigkeiten ins Arbeitsverzeichnis
COPY javax.jms-api-2.0.1.jar log4j-core-2.23.1.jar log4j-api-2.23.1.jar jedis-2.9.0.jar /app/
COPY apache-activemq-5.18.6-bin/apache-activemq-5.18.6/activemq-all-5.18.6.jar /app/
COPY apache-activemq-5.18.6-bin/apache-activemq-5.18.6/lib/activemq-client-5.18.6.jar /app/

# Kopiere die Gson JAR-Datei in das Arbeitsverzeichnis
COPY gson-2.11.0.jar /app/

# Kompiliere die Java-Dateien und setze den Klassenpfad für die JAR-Abhängigkeiten
RUN javac -cp ".:/app/*" EFD_Generator.java File_Reader.java Date.java EFD.java EventList.java

# Setze die Umgebungsvariable für ActiveMQ
ENV ACTIVEMQ_URL tcp://activemq:61616
ENV operationMode tcp

# Kopiere und erlaube das Skript auszuführen
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# Startkommando
CMD ["/app/start.sh"]
