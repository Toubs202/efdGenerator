#!/bin/sh
# Warte, bis ActiveMQ auf Port 61616 verfügbar ist
while ! nc -z activemq 61616; do
  echo "Warte auf ActiveMQ..."
  sleep 2
done

# Starte den EFD Generator
java -javaagent:/app/kieker-2.0.2-aspectj.jar -Dkieker.monitoring.writer.filesystem.FileWriter.customStoragePath=/app/ -Dkieker.monitoring.loggername=System.out -cp ".:javax.jms-api-2.0.1.jar:log4j-core-2.23.1.jar:log4j-api-2.23.1.jar:jedis-2.9.0.jar:activemq-all-5.18.6.jar:activemq-client-5.18.6.jar:gson-2.11.0.jar" EFD_Generator

