package hexlet.code.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
//import java.text.SimpleDateFormat;

public class TimeUtils {
    public static String getFormattedData(Timestamp timestamp) {
        return timestamp
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"));
        //return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
    }
    public static String getFormattedData(Instant instant) {
        return Timestamp.from(instant)
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"));
        //return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(instant);
    }   
}
