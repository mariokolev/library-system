package bg.tu.varna.informationSystem.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
