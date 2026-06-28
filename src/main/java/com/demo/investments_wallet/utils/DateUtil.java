package com.demo.investments_wallet.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Slf4j
public final class DateUtil {

    private DateUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static LocalDate parseLocalDate(String value) {
        try {
            if (isInvalid(value)) {
                return null;
            }

            return LocalDate.parse(value);

        } catch (DateTimeParseException ex) {
            log.error("Error parsing LocalDate from value: {}", value, ex);
            return null;
        }
    }

    public static LocalDateTime parseLocalDateTime(String value) {
        try {
            if (isInvalid(value)) {
                return null;
            }

            if (value.length() == 10) {
                return LocalDate.parse(value).atStartOfDay();
            }

            return LocalDateTime.parse(value);

        } catch (DateTimeParseException ex) {
            log.error("Error parsing LocalDateTime from value: {}", value, ex);
            return null;
        }
    }

    public static LocalDateTime parseDateToStartOfDay(String value) {
        try {
            LocalDate date = parseLocalDate(value);

            if (date == null) {
                return null;
            }

            return date.atStartOfDay();

        } catch (Exception ex) {
            log.error("Error converting date to start of day. Value: {}", value, ex);
            return null;
        }
    }

    public static LocalDateTime toEndOfDay(LocalDateTime dateTime) {
        try {
            if (dateTime == null) {
                return null;
            }

            return dateTime.with(LocalTime.of(23, 59, 59, 999999999));

        } catch (Exception ex) {
            log.error("Error converting LocalDateTime to end of day. Value: {}", dateTime, ex);
            return null;
        }
    }

    private static boolean isInvalid(String value) {
        return value == null
                || value.isBlank()
                || "null".equalsIgnoreCase(value.trim());
    }
}