package com.toolrentals.pos.business.calculator;

import com.toolrentals.pos.common.model.DeWaltJackhammer;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.StihlChainsaw;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the functionality of the methods in the RentalChargeCalculator class.
 */
public class RentalChargeCalculatorTest {
    public RentalChargeCalculator calculator = new RentalChargeCalculator();

    // for testing calculating the daily rates, using DeWalt Jackhammer, as it has no charge on holidays or weekends

    @Test
    public void testCalculateDailyRatesCharge1Day() {
        // setting up minimal rental agreement for DewaltJackjammer, known non-holiday or weekend
        Date checkoutDate = createCalendar(0, 0, 0, 0, 1, Calendar.AUGUST, 2024).getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(1)
                .checkoutDate(checkoutDate);

        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(1, agreement.getNumberChargeableDays());
        assertEquals(1, agreement.getRentalChargesByDate().size());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("2.99"));
    }

    @Test
    public void testCalculateDailyRatesNoWeekendCharge() {
        // test dewalt jackhammer weekend charge only gives 2 days with 0 charge - will reuse Calendar
        Calendar cal = createCalendar(0, 0, 0, 0, 3, Calendar.AUGUST, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(2)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(0, agreement.getNumberChargeableDays());
        assertEquals(2, agreement.getRentalChargesByDate().size());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
    }

    @Test
    public void testCalculateDailyRatesFullWeekWithWeekend() {
        // test DeWalt jackhammer 2 days 0 charge, 4 days full charge - will reuse Calendar
        Calendar cal = createCalendar(0, 0, 0, 0, 1, Calendar.AUGUST, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(7)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(5, agreement.getNumberChargeableDays());
        assertEquals(7, agreement.getRentalChargesByDate().size());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
    }

    @Test
    public void testCalculateDailyRatesHolidayNoCharge() {
        // DeWalt Jackhammer is 0 charge on holiday
        Calendar cal = createCalendar(0, 0, 0, 0, 4, Calendar.JULY, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(1)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(0, agreement.getNumberChargeableDays());
        assertEquals(1, agreement.getRentalChargesByDate().size());
        // daily rental rate for holiday day for Dewalt Jackhammer is 0
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("0"));
    }

    @Test
    public void testCalculateDailyRatesHolidayAndWeekend() {
        // DeWalt Jackhammer is 0 charge on holidays and weekends, 2.99 otherwise
        Calendar cal = createCalendar(0, 0, 0, 0, 1, Calendar.JULY, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(7)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertFalse(agreement.getRentalChargesByDate().isEmpty());
        assertEquals(7, agreement.getRentalChargesByDate().size());
        assertEquals(4, agreement.getNumberChargeableDays());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99, 0 for holiday, 0 for weekend
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("2.99"));
        // increment the calendar by 1 day for each assertion
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
    }

    @Test
    public void testCalculateNoChargeLaborDay() {
        // DeWalt Jackhammer is 0 charge on labor day (holiday) - Labor Day was Sept 4th, in 2023
        Calendar cal = createCalendar(0, 0, 0, 0, 4, Calendar.SEPTEMBER, 2023);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(1)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(0, agreement.getNumberChargeableDays());
        assertEquals(1, agreement.getRentalChargesByDate().size());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99, 0 for holiday, 0 for weekend
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("0"));
    }

    @Test
    public void testCalculateChargeLaborDay() {
        // for this one, using Stihl Chainsaw, as it charges on holidays, 1.49
        Calendar cal = createCalendar(0, 0, 0, 0, 4, Calendar.SEPTEMBER, 2023);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new StihlChainsaw())
                .rentalDays(1)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(1, agreement.getNumberChargeableDays());
        assertEquals(1, agreement.getRentalChargesByDate().size());
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("1.49"));
    }

    @Test
    public void testCalculateSubtotal() {
        Calendar cal = createCalendar(0, 0, 0, 0, 1, Calendar.AUGUST, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(1)
                .checkoutDate(checkoutDate)
                .percentDiscount(0);
        // calculate the daily rates
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertEquals(1, agreement.getNumberChargeableDays());
        assertEquals(1, agreement.getRentalChargesByDate().size());
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("2.99"));

        // calculate the pre-discount charge
        calculator.calculateSubtotal(agreement);
        assertTrue(agreement.hasSubtotal());
        // 1 day charge with Stihl Chainsaw is 1.49
        assertEquals(new BigDecimal("2.99"), agreement.getSubtotal());
    }

    @Test
    public void testCalculateSubtotal1HolidayWeek() {
        // DeWalt Jackhammer is 0 charge on holidays and weekends, 2.99 otherwise
        Calendar cal = createCalendar(0, 0, 0, 0, 1, Calendar.JULY, 2024);
        Date checkoutDate = cal.getTime();

        RentalAgreement agreement = new RentalAgreement()
                .tool(new DeWaltJackhammer())
                .rentalDays(7)
                .checkoutDate(checkoutDate);
        calculator.calculateDailyRates(agreement);
        assertTrue(agreement.hasDailyRentalCharges());
        assertFalse(agreement.getRentalChargesByDate().isEmpty());
        assertEquals(7, agreement.getRentalChargesByDate().size());
        assertEquals(4, agreement.getNumberChargeableDays());
        // daily rental rate for chargeable day for Dewalt Jackhammer is 2.99, 0 for holiday, 0 for weekend
        assertEquals(agreement.getRentalChargesByDate().get(checkoutDate), new BigDecimal("2.99"));
        // increment the calendar by 1 day for each assertion
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("2.99"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(agreement.getRentalChargesByDate().get(cal.getTime()), new BigDecimal("0"));

        // calculate the subtotal
        calculator.calculateSubtotal(agreement);
        assertTrue(agreement.hasSubtotal());
        // 4 charge days for DeWalt Jackhammer, which is 2.99 per day charged
        BigDecimal subtotal = new BigDecimal("2.99").add(new BigDecimal("2.99")).add(new BigDecimal("2.99")).add(new BigDecimal("2.99"));
        assertEquals(subtotal, agreement.getSubtotal());
    }

    @Test
    public void testCalculateNoDiscount() {
        RentalAgreement rentalAgreement = new RentalAgreement()
                .percentDiscount(0)
                .subtotal(new BigDecimal(100));
        calculator.calculateDiscount(rentalAgreement);
        assertTrue(rentalAgreement.hasDiscount());
        assertEquals(new BigDecimal("0.00"), rentalAgreement.getDiscount());
    }

    @Test
    public void testCalculateDiscount() {
        RentalAgreement rentalAgreement = new RentalAgreement()
                .percentDiscount(10)
                .subtotal(new BigDecimal(100));
        calculator.calculateDiscount(rentalAgreement);
        assertTrue(rentalAgreement.hasDiscount());
        assertEquals(new BigDecimal("10.00"), rentalAgreement.getDiscount());
    }

    @Test
    public void testCalculateTotalNoDiscount() {
        RentalAgreement agreement = new RentalAgreement()
                .discount(new BigDecimal(0))
                .subtotal(new BigDecimal(100));
        calculator.calculateTotal(agreement);
        assertTrue(agreement.hasTotal());
        assertEquals(new BigDecimal(100), agreement.getTotal());
    }

    @Test
    public void testCalculateTotalDiscount() {
        RentalAgreement agreement = new RentalAgreement()
                .discount(new BigDecimal(10))
                .subtotal(new BigDecimal(100));
        calculator.calculateTotal(agreement);
        assertTrue(agreement.hasTotal());
        assertEquals(new BigDecimal(90), agreement.getTotal());
    }

    private Calendar createCalendar(int millisecond, int seconds, int minutes, int hour, int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, millisecond);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal;
    }
}
