package hexlet.code.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final String PATTERN_FORMAT = "dd.MM.yyyy";
    public static String getFormattedData(Instant instant) {
        String formatted = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .format(instant.atOffset(ZoneOffset.UTC));
        return formatted;
    }
}
