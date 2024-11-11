import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Date {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    String dateTimeString = "2024-09-05 04:42:00";

    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);


    public static long substractDates(String date1, String date2) {
    	LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
    	LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
    	System.out.println(dateTime1);
    	Duration result = Duration.between(dateTime1, dateTime2);
    	return result.getSeconds();	
    }
}
