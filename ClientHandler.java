import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

static class ClientHandler implements Runnable {
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
        		if(line.equals("")) {
        			break;
        		}
        	    messageBuilder.append(line).append("\n");  // Alle eingehenden Zeilen sammeln
        	}
        	
        	String message = messageBuilder.toString();
        	
        	String[] lines = message.split("\n");
            if (lines.length >= 2) {
                // Zugriff auf die zweite Zeile (Index 1, da Arrays nullbasiert sind)
                String flightID = lines[1];
                String messageType = lines[0];
                //TODO umwandlung zu ArrayList<String>
                
                System.out.println("Msg: "+message);

                if ("flightPlan".equals(messageType)) {
                    // Verarbeite das flightPlan
                    processFlightPlan(flightID, message);
                    out.println("FlightPlan verarbeitet und gespeichert.");
                } else if ("flightData".equals(messageType)) {
                    // Verarbeite die flightData
                    processFlightData(flightID, message);
                    out.println("FlightData verarbeitet.");
                } else {	
                    out.println("Unbekannter Nachrichtentyp.");
                }
            } else {
                System.out.println("Der String enthält weniger als zwei Zeilen.");
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
