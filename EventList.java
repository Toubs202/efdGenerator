import java.util.HashMap;
import java.util.Map;

public class EventList {

    private static final Map<String, String> eventTypeMap = new HashMap<>();

    static {
        eventTypeMap.put("ACH", "MSG");
        eventTypeMap.put("ADI", "MSG");
        eventTypeMap.put("AFI", "MSG");
        eventTypeMap.put("APL", "MSG");
        eventTypeMap.put("APR", "MSG");
        eventTypeMap.put("ATT", "MAN");
        eventTypeMap.put("AXT", "MAN");
        eventTypeMap.put("CAL", "MAN");
        eventTypeMap.put("CDI", "MSG");
        eventTypeMap.put("CEO", "MAN");
        eventTypeMap.put("CDC", "REG");
        eventTypeMap.put("CNC", "REG");
        eventTypeMap.put("CPR", "MSG");
        eventTypeMap.put("CPT", "MAN");
        eventTypeMap.put("CSC", "REG");
        eventTypeMap.put("CTD", "MSG");
        eventTypeMap.put("EDI", "MSG");
        eventTypeMap.put("DAU", "MAN");
        eventTypeMap.put("FCM", "MSG");
        eventTypeMap.put("FDI", "MSG");
        eventTypeMap.put("FLS", "MSG");
        eventTypeMap.put("FSA", "MSG");
        eventTypeMap.put("GAI", "MSG");
        eventTypeMap.put("IAR", "MSG");
        eventTypeMap.put("ICA", "MSG");
        eventTypeMap.put("ICH", "MSG");
        eventTypeMap.put("DIE", "MSG");
        eventTypeMap.put("IDL", "MSG");
        eventTypeMap.put("IDE", "MSG");
        eventTypeMap.put("IFP", "MSG");
        eventTypeMap.put("MET", "SYS");
        eventTypeMap.put("OAI", "MSG");
        eventTypeMap.put("OAR", "MAN");
        eventTypeMap.put("OCA", "MAN");
        eventTypeMap.put("OCM", "MAN");
        eventTypeMap.put("ODA", "MAN");
        eventTypeMap.put("OEX", "MAN");
        eventTypeMap.put("OER", "MAN");
        eventTypeMap.put("OIC", "MAN");
        eventTypeMap.put("ONR", "MAN");
        eventTypeMap.put("ORX", "MAN");
        eventTypeMap.put("PDI", "MSG");
        eventTypeMap.put("PTX", "SYS");
        eventTypeMap.put("REA", "MSG");
        eventTypeMap.put("RFR", "MAN");
        eventTypeMap.put("RJT", "MSG");
        eventTypeMap.put("RRM", "MSG");
        eventTypeMap.put("RSI", "REG");
        eventTypeMap.put("SIP", "REG");
        eventTypeMap.put("SIT", "REG");
        eventTypeMap.put("SMM", "MSG");
        eventTypeMap.put("SPA", "MSG");
        eventTypeMap.put("SSP", "REG");
        eventTypeMap.put("SUS", "REG");
        eventTypeMap.put("TAI", "MSG");
        eventTypeMap.put("TAM", "SYS");
        eventTypeMap.put("TDE", "SYS");
        eventTypeMap.put("TDI", "MSG");
        eventTypeMap.put("TSA", "MSG");
        eventTypeMap.put("TTE", "SYS");
        eventTypeMap.put("UCD", "SYS");
        eventTypeMap.put("UFA", "MAN");
        eventTypeMap.put("UXC", "MAN");
    }

    /**
     * Methode zur Überprüfung des Eventtyps.
     * @param eventName Der Name des Events, z.B. "IDL"
     * @param expectedType Der erwartete Eventtyp, z.B. "MSG"
     * @return true, wenn der Typ übereinstimmt; false sonst
     */
    public static boolean checkEventType(String eventName, String expectedType) {
        String actualType = eventTypeMap.get(eventName);
        
        return expectedType.equals(actualType);
    }
    
    public static String getEventType(String eventName) {
    	return eventTypeMap.get(eventName);
    }

}
