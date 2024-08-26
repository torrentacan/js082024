package com.toolrentals.pos.business.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the functions in the DateUtil class.
 */
public class DateUtilTest {

    private final DateUtil dateUtil = new DateUtil();

    /**
     * Test whether the supplied date is a holiday. Holidays to be included: Independence Day and Labor Day
     */
    @Test
    public void testIsHoliday() {
        // Test date not a holiday
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 1); // January 1, 2023
        assertFalse(dateUtil.isHoliday(cal), "January 1 should not be a holiday");

        // Test Labor Day 2023
        cal.set(2023, Calendar.SEPTEMBER, 4); // September 4, 2023
        assertTrue(dateUtil.isHoliday(cal), "September 4, 2023 should be Labor Day");

        // Test Independence Day
        cal.set(2023, Calendar.JULY, 4); // July 4, 2023
        assertTrue(dateUtil.isHoliday(cal), "July 4, 2023 should be Independence Day");

        // Test Labor Day 2024
        cal.set(2024, Calendar.SEPTEMBER, 2); // September 2, 2024
        assertTrue(dateUtil.isHoliday(cal), "September 2, 2024 should be Labor Day");

        // Test a date that should not be a holiday
        cal.set(2024, Calendar.MAY, 15); // May 15, 2024
        assertFalse(dateUtil.isHoliday(cal), "May 15, 2024 should not be a holiday");
    }

    /**
     * Test the function to find the date of Labor Day for a given year.
     */
    @Test
    public void testFindLaborDay() {
        // Labor Day 2023 is September 4th
        Calendar laborDay = dateUtil.findLaborDay(2023);
        assertEquals(Calendar.SEPTEMBER, laborDay.get(Calendar.MONTH), "Month should be September");
        assertEquals(4, laborDay.get(Calendar.DAY_OF_MONTH), "Day should be 4th");

        // Labor Day 2024 is September 2nd
        laborDay = dateUtil.findLaborDay(2024);
        assertEquals(Calendar.SEPTEMBER, laborDay.get(Calendar.MONTH), "Month should be September");
        assertEquals(2, laborDay.get(Calendar.DAY_OF_MONTH), "Day should be 2nd");

        // Test Labor Day for a non-leap year
        laborDay = dateUtil.findLaborDay(2022);
        assertEquals(Calendar.SEPTEMBER, laborDay.get(Calendar.MONTH), "Month should be September");
        assertEquals(5, laborDay.get(Calendar.DAY_OF_MONTH), "Day should be 5th");

        // Test Labor Day for another year
        laborDay = dateUtil.findLaborDay(2025);
        assertEquals(Calendar.SEPTEMBER, laborDay.get(Calendar.MONTH), "Month should be September");
        assertEquals(1, laborDay.get(Calendar.DAY_OF_MONTH), "Day should be 1st");
    }

}
