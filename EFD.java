import java.util.ArrayList;
import java.util.List;

public class EFD {
    private String Ifplid; //flugplan ID
    private String Arcid; //Aircraft ID
    private String Arctype; //Aircraft Type
    private String reg; //Most Penalising Regulations
    private String Adep; //Abflug
    private String Ades; //Ankunft
    private String Eobd; //Estiamted Offblocktime
    private String Eobt;
    private String Iobt; //IFPS Offblocktime
    private String Iobd;
    private String Event;
    private String Eventclass;
    private String Timestamp;
    private String Fltstate; //Status des Flugs aus ATFM (FI, FS, SI, TA, AA, CA, TE, SU)
    private String Rdystate; //readystatus D, N, I, S
    private String Modeltyp;
    private String Cobd; //Calculated OffblockTime
    private String Cobt;
    private String Aobt; //Actual Offblocktime
    private String Aobd;
    private String Eda; //Estimated time of arrival
    private String Eta;
    private String Cda; //Calculated time of arrival
    private String Cta;
    private String Ada; //Actual time of arrival
    private String Ata;
    private String Rtepts; //Routenpunkte
    private String Asplist; //Liste an Airspaces
    private String regul; //Identifier einer Regulation    
    private List<String> Afregullist; //nur wenn flug nicht reguliert ist
    private List<String> Geo; //Geoinformationen
    private String Taxitime; //Zeit zum Taxin
    private String Atfmdelay; //Delay des Fluges
    private String Irules = "not available";
    private String Flttype = "not available"; //s n g m x
    private String sensitive = "not available";
    private String Aoopr; //Icao designator of  AircraftOperator
    private String Aoarcid;
    private String Adesolt;
    private String Depaptype;
    private String Cdmstatus;
    private String Depreg;
    private String Deparctype;
    private String Ifpsdiscrepancy;
    private String Depstatus;
    private String Tobt;
    private String Tsat;
    private String PRF1;
    private String PRF2;
    private String PRF3;
    private String PRF4;
    
    private String actualTakeOffTime;
    private String AtaFull;

    private EFD(Builder builder) {
        this.Adep = builder.Adep;
        this.Ades = builder.Ades;
        this.Arcid = builder.Arcid;
        this.Adesolt = builder.Adesolt;
        this.Afregullist = builder.Afregullist;
        this.Aoarcid = builder.Aoarcid;
        this.Aoopr = builder.Aoopr;
        this.Aobd = builder.Aobd;
        this.Aobt = builder.Aobt;
        this.Asplist = builder.Asplist;
        this.Atfmdelay = builder.Atfmdelay;
        this.Cdmstatus = builder.Cdmstatus;
        this.Depaptype = builder.Depaptype;
        this.Depreg = builder.Depreg;
        this.Depstatus = builder.Depstatus;
        this.Event = builder.Event;
        this.Eventclass = builder.Eventclass;
        this.Fltstate = builder.Fltstate;
        this.Ifpsdiscrepancy = builder.Ifpsdiscrepancy;
        this.Iobt = builder.Iobt;
        this.Iobd = builder.Iobd;
        this.Modeltyp = builder.Modeltyp;
        this.PRF1 = builder.PRF1;
        this.PRF2 = builder.PRF2;
        this.PRF3 = builder.PRF3;
        this.PRF4 = builder.PRF4;
        this.Rdystate = builder.Rdystate;
        this.Rtepts = builder.Rtepts;
        this.Timestamp = builder.Timestamp;
        this.Tobt = builder.Tobt;
        this.Tsat = builder.Tsat;
        this.Ifplid = builder.Ifplid;
        this.Arctype = builder.Arctype;
        this.reg = builder.reg;
        this.Eobd = builder.Eobd;
        this.Eobt = builder.Eobt;
        this.Cobd = builder.Cobd;
        this.Cobt = builder.Cobt;
        this.Eta = builder.Eta;
        this.Eda = builder.Eda;
        this.Cda = builder.Cda;
        this.Cta = builder.Cta;
        this.Ada = builder.Ada;
        this.Ata = builder.Ata;
        this.regul = builder.regul;
        this.Geo = builder.Geo;
        this.Taxitime = builder.Taxitime;
        this.Deparctype = builder.Deparctype;
        
        this.actualTakeOffTime = builder.actualTakeOffTime;
        this.AtaFull = builder.AtaFull;
    }

    // Static Builder Class
    public static class Builder {
        private String Ifplid; //flugplan ID
        private String Arcid; //Aircraft ID
        private String Arctype; //Aircraft Type
        private String reg; //Most Penalising Regulations
        private String Adep; //Abflug
        private String Ades; //Ankunft
        private String Eobd; //Estiamted Offblocktime
        private String Eobt;
        private String Iobt; //IFPS Offblocktime
        private String Iobd;
        private String Event;
        private String Eventclass;
        private String Timestamp;
        private String Fltstate; //Status des Flugs aus ATFM (FI, FS, SI, TA, AA, CA, TE, SU)
        private String Rdystate; //readystatus D, N, I, S
        private String Modeltyp; //EST CAL ACT
        private String Cobd; //Calculated OffblockTime
        private String Cobt;
        private String Aobt; //Actual Offblocktime
        private String Aobd;
        private String Eda; //Estimated time of arrival
        private String Eta;
        private String Cda; //Calculated time of arrival
        private String Cta;
        private String Ada; //Actual time of arrival
        private String Ata;
        private String Rtepts; //Routenpunkte
        private String Asplist; //Liste an Airspaces
        private String regul; //Identifier einer Regulation    
        private List<String> Afregullist; //nur wenn flug nicht reguliert ist
        private List<String> Geo; //Geoinformationen
        private String Taxitime; //Zeit zum Taxin
        private String Atfmdelay; //Delay des Fluges
        private String Aoopr; //Icao designator of  AircraftOperator
        private String Aoarcid;
        private String Adesolt;
        private String Depaptype;
        private String Cdmstatus;
        private String Depreg;
        private String Deparctype;
        private String Ifpsdiscrepancy;
        private String Depstatus;
        private String Tobt;
        private String Tsat;
        private String PRF1;
        private String PRF2;
        private String PRF3;
        private String PRF4;
        
        private String actualTakeOffTime;
        private String AtaFull;
        
        

        public Builder(ArrayList<String[]> flightPlan, ArrayList<String[]> flightData) {
        	String date;
        	String time;
            for (String[] keyString : flightPlan) {
                String key = keyString[0];
                String value = keyString[1];
                switch (key) {
                    case "flightPlan.flightPlan.aerodromesOfDestination.aerodromeOfDestination.icaoId":
                        this.Ades = value;
                        break;
                    case "flightPlan.flightPlan.aerodromeOfDeparture.icaoId":
                        this.Adesolt = value;
                        this.Adep = value;
                        break;
                    case "flightPlan.operatingAircraftOperator":
                    	this.Aoopr = value;
                        break;
                    case "flightPlan.flightPlan.ifplId":
                    	this.Ifplid = value;
                    	break;
                    case "flightPlan.flightPlan.aircraftId.aircraftId":
                    	this.Arcid = value;
                    default:
                        break;
                }
            }
            for (String[] keyString : flightData) {
                String key = keyString[0];
                String value = keyString[1];
                switch (key) {
                    case "flightData.aircraftAddress":
                        this.Depreg = value;
                        break;
                    case "flightData.aircraftOperator":
                    	this.Aoarcid = value;
                    case "flightData.aircraftType":
                        this.Depaptype = value;
                        this.Deparctype = value;
                        this.Arctype = value;
                        break;
                    case "flightData.calculatedOffBlockTime":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                    	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                        this.Iobd = date;
                        this.Iobt = time;
                        this.Tobt = date;
                        this.Tsat = time;
                        this.Cobt = time;
                        this.Cobd = date;
                        break;
                    case "flightData.actualTakeOffTime":
                       	 date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                    	 time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                    	 this.actualTakeOffTime = value;
                    	break;
                    case "flightData.flightId.keys.estimatedOffBlockTime":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                   	 	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                   	 	this.Eobt = time;
                   	 	this.Eobd = date;
                    	break;
                    case "flightData.estimatedTakeOffTime":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                   	 	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());

                    	break;
                    case "flightData.calculatedTakeOffTime":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                      	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                    	break;
                    case "flightData.calculatedTimeOfArrival":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                      	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                    	this.Cta = time;
                    	this.Cda = date;                    			
                    	break;
                    case "flightData.estimatedTimeOfArrival":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                      	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                    	this.Eda = date;
                    	this.Eta = time;
                    	break;
                    case "flightData.actualTimeOfArrival":
                    	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                      	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                      	this.Ata = time;
                      	this.AtaFull = value;
                      	this.Ada = time;         	
                    	break;
                    case "flightData.regulationLocations.regulationId":
                    	this.Afregullist = new ArrayList<String>();
                        if (value != null && !value.isEmpty()) {
                            this.Afregullist.add(value);
                        }
                        break;
                    case "flightData.mostPenalisingRegulation":
                    	this.regul = value;
                    	break;
                    case "filedRegistrationMark":
                    	this.reg = value;
                    	break;
                    case "eventType":
                        this.Event = value;
                        this.Eventclass = EventList.getEventType(value);
                        break;
                    case "flightData.ifpsDiscrepancy":
                        this.Ifpsdiscrepancy = value;
                        break;
                    case "flightData.flightState":
                        this.Fltstate = value;
                        this.Cdmstatus = value;
                        this.Depstatus = value;
                        this.Rdystate = value;
                        break;
                    case "flightData.delay":
                        this.Atfmdelay = value;
                        break;
                    case "flightData.actualOffBlockTime":
                      	date = value.substring(2, 4) + value.substring(5, 7) + value.substring(8, 10);
                      	time = value.substring(value.length() - 5, value.length() - 3)+value.substring(value.length() - 2, value.length());
                        this.Aobd = date;
                        this.Aobt = time;
                    break;
                    case "flightData.divertedAerodromeOfDestination":
                    	this.Adesolt = value;
                    	break;
                    case "flightData.currentDepartureTaxiTimeAndProcedure.taxiTime":
                    	this.Taxitime = value;
                    case "timestamp":
                        this.Timestamp = value;
                        break;
                    default:
                        break;
                }

            }
            System.out.println(this.Ata+this.actualTakeOffTime);
            this.PRF1 = Long.toString(Date.substractDates(this.AtaFull, this.actualTakeOffTime));
        }

        public EFD build() {
            return new EFD(this);
        }
    }
}
