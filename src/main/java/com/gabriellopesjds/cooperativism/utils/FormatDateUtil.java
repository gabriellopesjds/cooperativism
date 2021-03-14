package com.gabriellopesjds.cooperativism.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FormatDateUtil {

    public static String localDateTimeToStringUtc(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date parameter cannot be null");
        }

        OffsetDateTime offsetDateTime = OffsetDateTime.of(date, ZoneOffset.UTC);
        return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
