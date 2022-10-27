package com.clouddevg.ita.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Returns today's date as java.util.Date object
     *
     * @return today's date as java.util.Date object
     */
    public static LocalDate today() {

        return LocalDate.now();
    }

    /**
     * Returns today's date as yyyy-MM-dd format
     *
     * @return today's date as yyyy-MM-dd format
     */
    public static String todayStr() {
        return LocalDate.now().toString();
    }

    /**
     * Returns the formatted String date for the passed java.util.Date object
     *
     * @param date
     * @return
     */
    public static String formattedDate(LocalDate date) {
        return date != null ? date.toString() : todayStr();
    }
}
