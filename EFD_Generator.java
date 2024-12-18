import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import redis.clients.jedis.Jedis;

import org.apache.activemq.ActiveMQConnectionFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EFD_Generator {
    private static Jedis redisClient;
    private static final int TCP_PORT = 9091;

    static {
        redisClient = new Jedis("redis", 6379); // Verbindung zu Redis
    }

    // Umgebungsvariable operationMode einlesen (steuert, ob mit Dateien gearbeitet wird oder der TCP-Server gestartet wird)
    private static final String OPERATION_MODE = System.getenv("operationMode");

    // Flugplan in Redis speichern
    public static void storeFlightPlan(String flightId, ArrayList<String[]> flightPlan) throws Exception {
        String jsonFlightPlan = new Gson().toJson(flightPlan);
        redisClient.setex(flightId, 86400, jsonFlightPlan);
    }

    // Flugplan aus Redis abrufen
    public static ArrayList<String[]> retrieveFlightPlan(String flightId) throws Exception {
    	System.out.println("Retrieving FlightPlan");
        String jsonFlightPlan = redisClient.get(flightId);
        if (jsonFlightPlan == null) return null;
        return new Gson().fromJson(jsonFlightPlan, new TypeToken<ArrayList<String[]>>() {}.getType());
    }

    // Flug ID aus dem Flugplan extrahieren
    public static String getFlightID(ArrayList<String[]> flightPlan) {
        for (String[] keyString : flightPlan) {
            if ("flightPlan.flightPlan.ifplId".equals(keyString[0])) {
                return keyString[1];
            }
        }
        return "-1";
    }

    // Funktion zur Testen der Redis-Daten
    public static void testRedis(ArrayList<String[]> flightPlan, String flightID) {
        try {
            ArrayList<String[]> redisFlightPlan = retrieveFlightPlan(flightID);
            if (redisFlightPlan == null || redisFlightPlan.size() != flightPlan.size()) {
                System.out.println("Die Listen stimmen nicht überein.");
                return;
            }
            for (int i = 0; i < redisFlightPlan.size(); i++) {
                if (!Arrays.equals(redisFlightPlan.get(i), flightPlan.get(i))) {
                    System.out.println("Unterschied bei Element " + i);
                    return;
                }
            }
            System.out.println("Die Listen stimmen überein.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hauptmethode
    public static void main(String[] args) {
        try {
            if ("file".equalsIgnoreCase(OPERATION_MODE)) {
                processFiles();
            } else if ("tcp".equalsIgnoreCase(OPERATION_MODE)) {
                startTCPServer();
            } else {
                System.out.println("Ungültiger operationMode: " + OPERATION_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Verarbeitet die Dateien und speichert die Daten in Redis
    private static void processFiles() throws Exception {
        // Flugplan und Daten aus den Dateien laden
        File_Reader fD = new File_Reader("./oneFlightData.xml");
        ArrayList<String[]> flightData = fD.mapFile();

        File_Reader fP = new File_Reader("./flightplan.xml");
        ArrayList<String[]> flightPlan = fP.mapFile();

        // Flugplan in Redis speichern
        storeFlightPlan(getFlightID(flightPlan), flightPlan);

        // EFD-Objekt erstellen und in einen String umwandeln
        EFD efd = new EFD.Builder(flightPlan, flightData).build();
        String efdString = convertEFDToString(efd);

        // EFD in eine Datei schreiben
        writeToFile(efdString);

        // EFD an ActiveMQ senden
        sendToActiveMQ(efdString);

        // Redis-Test durchführen
        testRedis(flightPlan, getFlightID(flightPlan));
    }

    // EFD in einen String umwandeln
    private static String convertEFDToString(EFD efd) {
        StringBuilder efdString = new StringBuilder("-TITLE EFD\n");
        Field[] fields = efd.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                efdString.append("-").append(field.getName()).append(" ").append(field.get(efd)).append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return efdString.toString();
    }

    // EFD-String in eine Datei schreiben
    private static void writeToFile(String efdString) {
        Path path = Paths.get(System.getProperty("user.dir") + "/efd.txt");
        try {
            Files.writeString(path, efdString, StandardCharsets.UTF_8);
            System.out.println("EFD erfolgreich in die Datei geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Schreiben der Datei.");
        }
    }
    
    // Verarbeitet den flightPlan und speichert ihn in Redis
     static void processFlightPlan(String flightID, String flightPlanMessage) {
        try {
            Gson gson = new Gson();
            File_Reader flightPlanReader = new File_Reader(flightPlanMessage);
            ArrayList<String[]> flightPlan = flightPlanReader.mapXML(flightPlanMessage);
            System.out.println(flightPlan);
            // Speichern des flightPlans in Redis
            storeFlightPlan(flightID, flightPlan);

            System.out.println("FlightPlan für " + flightID + " gespeichert.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFD-String an ActiveMQ senden
    private static void sendToActiveMQ(String efdString) {
        String brokerURL = System.getenv("ACTIVEMQ_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://activemq:61616";
        }

        String queueName = "EFDQueue";
        try (Connection connection = new ActiveMQConnectionFactory(brokerURL).createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(efdString);
            producer.send(message);
            System.out.println("EFD-Nachricht erfolgreich an ActiveMQ gesendet.");
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Senden der Nachricht an ActiveMQ.");
        }
    }
    
     static void processFlightData(String flightID, String flightDataMessage) {
        try {
        	System.out.println("Processing Flight Data: "+flightDataMessage);
            // Umwandeln der XML-Nachricht in flightData (ArrayList<String[]>)
        	// TODO umwandeln von flightDataString in ArrayList und dann EFD erstellen;
            Gson gson = new Gson();
            File_Reader flightDataReader = new File_Reader(flightDataMessage);
            ArrayList<String[]> flightData = flightDataReader.mapXML(flightDataMessage);
            // FlightPlan aus Redis holen
            ArrayList<String[]> flightPlan = retrieveFlightPlan(flightID);
            if (flightPlan == null) {
                System.out.println("Kein FlightPlan für " + flightID + " gefunden.");
            }	
            else {
                // EFD-Objekt erstellen
                EFD efd = new EFD.Builder(flightPlan, flightData).build();

                // EFD in einen String umwandeln
                String efdString = convertEFDToString(efd);

                // EFD-String an ActiveMQ senden
                sendToActiveMQ(efdString);
                System.out.println("EFD für " + flightID + " an ActiveMQ gesendet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // TCP-Server starten
    private static void startTCPServer() {
        System.out.println("Starte den TCP-Server...");
        new Thread(new TCPServer()).start();
    }
}

