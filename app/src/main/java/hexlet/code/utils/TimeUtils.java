package hexlet.code.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getFormattedData(Instant instant) {
        String formatted = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                .format(instant.atOffset(ZoneOffset.UTC));
        return formatted;
    }
}
