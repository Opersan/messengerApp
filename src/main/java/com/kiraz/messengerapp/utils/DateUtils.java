package com.kiraz.messengerapp.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static Instant convertStringDate(String date) {
        String pattern = "hh:mm:ss a, EEE M/d/uuuu";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        ZoneId zoneId = ZoneId.of("Europe/Istanbul");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();

        return instant;
    }
}
