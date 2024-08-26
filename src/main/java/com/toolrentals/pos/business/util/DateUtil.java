package com.toolrentals.pos.business.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class contains all of the methods needed for date manipulation.
 */
public class DateUtil {

    /**
     * Determine if the supplied calendar date is a holiday. The observed holidays are Independence day and Labor Day.
     *
     * @param calendar Calendar - the calendar date in question
     * @return boolean - true if a holiday
     */
    public boolean isHoliday(Calendar calendar) {
        return isIndependenceDay(calendar) || isLaborDay(calendar);
    }

    /**
     * Utility method to determine if the rental day is Labor day
     *
     * @param calendar Calendar - the calendar date in question
     * @return boolean - true if calendar date is Labor day
     */
    public boolean isLaborDay(Calendar calendar) {
        boolean isLaborDay = false;
        if (calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
            isLaborDay = findLaborDay(calendar.get(Calendar.YEAR)).get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
        }
        return isLaborDay;
    }

    /**
     * Utility method to find labor day for the supplied year
     *
     * @param year int - the year to find Labor day in
     * @return Calendar - Labor day
     */
    public Calendar findLaborDay(int year) {
        Calendar laborDay = Calendar.getInstance();
        // set the day to the first day to ensure no unwanted month changes based on the month or year
        laborDay.set(Calendar.DAY_OF_MONTH, 1);
        // set the month to September
        laborDay.set(Calendar.MONTH, Calendar.SEPTEMBER);
        // set the year to the year that the rental day is occurring
        laborDay.set(Calendar.YEAR, year);
        // find the first Monday of September of the rental day year
        while (laborDay.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            laborDay.add(Calendar.DAY_OF_MONTH, 1);
        }
        return laborDay;
    }

    /**
     * Utility method to determine if the supplied calendar date is Independence day
     *
     * @param calendar Calendar - the calendar date in question
     * @return boolean - true if the calendar date is Independence day
     */
    public boolean isIndependenceDay(Calendar calendar) {
        return calendar.get(Calendar.MONTH) == Calendar.JULY && calendar.get(Calendar.DAY_OF_MONTH) == 4;
    }

    /**
     * Add the supplied number of days to the supplied date.
     *
     * @param date Date - the starting date to be manipulated
     * @param days int - the number of days to add to the Date
     * @return Date - the resulting date
     */
    public Date addDaysToDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * Format the supplied date in the form of MM/dd/yy
     * @param date Date - the date to format.
     * @return String - the formatted date string.
     */
    public String formatDateMMDDYY(Date date) {
        return new SimpleDateFormat("MM/dd/yy").format(date);
    }
}
