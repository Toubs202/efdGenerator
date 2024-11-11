import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static final int PORT = 9090; // Beispielport
    private static List<ContainerClient> containers = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("TCP-Server läuft auf Port " + PORT);
            while (true) {
                // Warten auf eingehende Verbindungen
                Socket clientSocket = serverSocket.accept();
                System.out.println("Verbindung von " + clientSocket.getInetAddress());
                
                // Jede eingehende Verbindung in einem neuen Thread behandeln
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler verarbeitet eingehende Nachrichten
    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Nachricht lesen
                String message = in.readLine();
                System.out.println("Empfangene Nachricht: " + message);

                // Auf Nachrichtinhalt prüfen und weiterleiten
                if (message.startsWith("flightPlan")) {
                    // flightPlan an alle Container senden
                    sendToAllContainers(message);
                } else if (message.startsWith("flightData")) {
                    // flightData an freien Container senden
                    sendToFreeContainer(message);
                } else {
                    out.println("Unbekannter Nachrichtentyp");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Sende flightPlan an alle Container
        private void sendToAllContainers(String message) {
            for (ContainerClient container : containers) {
                container.sendMessage(message);
            }
        }

        // Sende flightData nur an einen freien Container
        private void sendToFreeContainer(String message) {
            for (ContainerClient container : containers) {
                if (container.isFree()) {
                    container.sendMessage(message);
                    break;
                }
            }
        }
    }

    // Eine ContainerClient-Klasse für die Kommunikation mit den Docker-Containern
    static class ContainerClient {
        private String containerIp;
        private int containerPort;
        private boolean isFree;

        public ContainerClient(String containerIp, int containerPort) {
            this.containerIp = containerIp;
            this.containerPort = containerPort;
            this.isFree = true; // Standardmäßig frei
        }

        public boolean isFree() {
            return isFree;
        }

        public void setFree(boolean free) {
            this.isFree = free;
        }

        // Sende Nachricht an den Container
        public void sendMessage(String message) {
            try (Socket socket = new Socket(containerIp, containerPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println(message);
                System.out.println("Nachricht gesendet an Container " + containerIp + ":" + containerPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

