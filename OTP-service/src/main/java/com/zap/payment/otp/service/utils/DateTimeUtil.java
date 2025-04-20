package com.zap.payment.otp.service.utils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;

public class DateTimeUtil {

    public static LocalDate formatDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public static ZonedDateTime ceiling(ZonedDateTime input, TemporalField roundTo, int roundIncrement) {
        /* Extract the field being rounded. */
        int field = input.get(roundTo);

        /* Distance from previous floor. */
        int r = field % roundIncrement;

        /* Find floor and ceiling. Truncate values to base unit of field. */
        ZonedDateTime ceiling =
                input.plus(roundIncrement - r, roundTo.getBaseUnit())
                        .truncatedTo(roundTo.getBaseUnit());

        return ceiling;
    }

}
