import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
    	System.out.println("New Connection");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
        	StringBuilder messageBuilder = new StringBuilder();
        	String line;
        	while ((line = in.readLine()) != null) {
        		if(line.equals("<fl:FlightDataMessage xmlns:fw=\"eurocontrol/c...DataMessage>}")||line.equals("")) {
        			break;
        		}
        	    messageBuilder.append(line).append("\n");  // Alle eingehenden Zeilen sammeln
        	}
        	
        	String message = messageBuilder.toString();
        	
        	String[] lines = message.split("\n");
            if (lines.length >= 15) {
                // Zugriff auf die zweite Zeile (Index 1, da Arrays nullbasiert sind)
                String flightID = lines[15].replaceAll("<.*?>",  "").replaceAll("\\s+", "");
                String messageType = "";
                if(lines[1].contains("FlightData")) {
                	messageType = "flightData";
                } else if(lines[1].contains("FlightPlan")) {
                	messageType = "flightPlan";
                }
                //TODO umwandlung zu ArrayList<String>
                
                System.out.println("Msg: "+message);

                if ("flightPlan".equals(messageType)) {
                    // Verarbeite das flightPlan
                    EFD_Generator.processFlightPlan(flightID, message);
                    out.println("FlightPlan verarbeitet und gespeichert.");
                } else if ("flightData".equals(messageType)) {
                    // Verarbeite die flightData
                    EFD_Generator.processFlightData(flightID, message);
                    out.println("FlightData verarbeitet.");
                } else {	
                    out.println("Unbekannter Nachrichtentyp.");
                }
            } else {
                System.out.println("Der String enthält weniger als 15 Zeilen.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 }
