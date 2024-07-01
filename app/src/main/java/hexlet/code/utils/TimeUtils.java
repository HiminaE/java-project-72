package hexlet.code.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
    public static String getFormattedData(Timestamp timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(timestamp);
    }
}
