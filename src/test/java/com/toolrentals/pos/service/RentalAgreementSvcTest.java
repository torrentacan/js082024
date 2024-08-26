package com.toolrentals.pos.service;

import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.model.DeWaltJackhammer;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RentalAgreementSvcTest {

    private RentalAgreementSvcImpl svc = new RentalAgreementSvcImpl();

    @Test
    public void createRentalAgreement() {
        RentalAgreement rentalAgreement = svc.createRentalAgreement();
        assertNotNull(rentalAgreement);
    }

    @Test
    public void findTool() {
        Tool tool = svc.findTool(ToolCode.CHNS);
        assertNotNull(tool);

        tool = svc.findTool(ToolCode.LADW);
        assertNotNull(tool);

        tool = svc.findTool(ToolCode.JAKD);
        assertNotNull(tool);

        tool = svc.findTool(ToolCode.JAKR);
        assertNotNull(tool);
    }

    @Test
    public void assignToolToRentalAgreement() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool tool = new DeWaltJackhammer();
        assertThrows(
                Exception.class,
                () -> svc.assignToolToRentalAgreement(null, null),
                "Must create a rental agreement."
        );
        assertThrows(
                Exception.class,
                () -> svc.assignToolToRentalAgreement(rentalAgreement, null),
                "Must select a tool."
        );
        assertDoesNotThrow(
                () -> svc.assignToolToRentalAgreement(rentalAgreement, tool)
        );

        assertTrue(rentalAgreement.hasTool());
    }

    @Test
    public void testAssignRentalDaysToRentalAgreement() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> svc.assignRentalDaysToRentalAgreement(null, 10),
                "Must create a rental agreement."
        );
        assertThrows(
                Exception.class,
                () -> svc.assignRentalDaysToRentalAgreement(rentalAgreement, 0),
                "Must rent tool for at least 1 day."
        );
        assertDoesNotThrow(
                () -> svc.assignRentalDaysToRentalAgreement(rentalAgreement, 10)
        );
    }

    @Test
    public void testAssignCheckoutDateToRentalAgreement() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> svc.assignCheckoutDateToRentalAgreement(null, null),
                "Must create a rental agreement."
        );
        assertThrows(
                Exception.class,
                () -> svc.assignCheckoutDateToRentalAgreement(rentalAgreement, null),
                "Must select a checkout date."
        );

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.DAY_OF_MONTH, -1);
        assertThrows(
                Exception.class,
                () -> svc.assignCheckoutDateToRentalAgreement(rentalAgreement, cal.getTime()),
                "Checkout cannot be prior to current date."
        );

        assertDoesNotThrow(
                () -> svc.assignCheckoutDateToRentalAgreement(rentalAgreement, today)
        );
    }

    @Test
    public void testCalculateDueDate() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setTool(new DeWaltJackhammer());
        Date today = new Date();
        assertThrows(
                Exception.class,
                () -> svc.calculateDueDate(null),
                "Must create a rental agreement."
        );

        assertThrows(
                Exception.class,
                () -> svc.calculateDueDate(rentalAgreement),
                "Must supply checkout date and number of rental days."
        );

        rentalAgreement.setCheckoutDate(today);
        assertThrows(
                Exception.class,
                () -> svc.calculateDueDate(rentalAgreement),
                "Must supply number of rental days."
        );
        rentalAgreement.setCheckoutDate(null);
        rentalAgreement.setRentalDays(1);
        assertThrows(
                Exception.class,
                () -> svc.calculateDueDate(rentalAgreement),
                "Must supply checkout date."
        );
        rentalAgreement.setCheckoutDate(today);
        assertDoesNotThrow(
                () -> svc.calculateDueDate(rentalAgreement)
        );

        assertTrue(rentalAgreement.hasDueDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 1);

        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.setTime(rentalAgreement.getDueDate());

        assertEquals(cal.get(Calendar.YEAR), dueDateCal.get(Calendar.YEAR));
        assertEquals(cal.get(Calendar.MONTH), dueDateCal.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), dueDateCal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCalculateDailyRentalCharges() {
        RentalAgreement rentalAgreement = new RentalAgreement();

        assertThrows(
                Exception.class,
                () -> svc.calculateDailyRentalCharges(null),
                "Must create a rental agreement."
        );
        assertThrows(
                Exception.class,
                () -> svc.calculateDailyRentalCharges(rentalAgreement),
                "Must select a tool."
        );

        rentalAgreement.setTool(new DeWaltJackhammer());

        assertThrows(
                Exception.class,
                () -> svc.calculateDailyRentalCharges(rentalAgreement),
                "Must supply checkout date and number of rental days."
        );

        // setting the date to a known holiday week
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.YEAR, 2025);

        rentalAgreement.setCheckoutDate(cal.getTime());
        assertThrows(
                Exception.class,
                () -> svc.calculateDailyRentalCharges(rentalAgreement),
                "Must supply number of rental days."
        );
        rentalAgreement.setCheckoutDate(null);
        rentalAgreement.setRentalDays(7);
        assertThrows(
                Exception.class,
                () -> svc.calculateDailyRentalCharges(rentalAgreement),
                "Must supply checkout date."
        );

        rentalAgreement.setCheckoutDate(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        rentalAgreement.setDueDate(cal.getTime());

        assertDoesNotThrow(
                () -> svc.calculateDailyRentalCharges(rentalAgreement)
        );

        assertNotNull(rentalAgreement.getRentalChargesByDate());
        assertEquals(7, rentalAgreement.getRentalChargesByDate().size());
    }

    @Test
    public void testCalculateSubtotal() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setTool(new DeWaltJackhammer());

        assertThrows(
                Exception.class,
                () -> svc.calculateSubtotal(null),
                "Must create a rental agreement."
        );

        assertThrows(
                Exception.class,
                () -> svc.calculateSubtotal(rentalAgreement),
                "Must calculate the daily rates before calculating the subtotal."
        );

        rentalAgreement.setRentalDays(7);

        // setting the date to a known holiday week
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.YEAR, 2025);

        rentalAgreement.setCheckoutDate(cal.getTime());

        Map<Date, BigDecimal> dailyCharges = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            if (i == 3 || i == 5 || i == 6) {
                dailyCharges.put(cal.getTime(), BigDecimal.ZERO);
            } else {
                dailyCharges.put(cal.getTime(), new BigDecimal("2.99"));
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        rentalAgreement.setDueDate(cal.getTime());
        rentalAgreement.setRentalChargesByDate(dailyCharges);

        assertDoesNotThrow(
                () -> svc.calculateSubtotal(rentalAgreement)
        );

        assertEquals(new BigDecimal("11.96"), rentalAgreement.getSubtotal());
    }

    @Test
    public void testAssignPercentDiscount() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> svc.assignPercentDiscount(null, 0),
                "Must create a rental agreement."
        );
        assertThrows(
                Exception.class,
                () -> svc.assignPercentDiscount(rentalAgreement, -1),
                "Percent discount must be between 0 and 100."
        );
        assertThrows(
                Exception.class,
                () -> svc.assignPercentDiscount(rentalAgreement, 101),
                "Percent discount must be between 0 and 100."
        );

        assertDoesNotThrow(
                () -> svc.assignPercentDiscount(rentalAgreement, 10)
        );

        assertEquals(10, rentalAgreement.getPercentDiscount());
    }

    @Test
    public void testCalculateDiscountAmount() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> svc.calculateDiscountAmount(null),
                "Must create a rental agreement."
        );

        assertThrows(
                Exception.class,
                () -> svc.calculateDiscountAmount(rentalAgreement),
                "Must calculate subtotal before discount can be calculated."
        );

        rentalAgreement.setSubtotal(new BigDecimal("11.96"));
        // test no discount
        assertDoesNotThrow(
                () -> svc.calculateDiscountAmount(rentalAgreement)
        );
        assertEquals(new BigDecimal("0.00"), rentalAgreement.getDiscount());

        // test 10% discount
        rentalAgreement.setPercentDiscount(10);
        assertDoesNotThrow(
                () -> svc.calculateDiscountAmount(rentalAgreement)
        );
        assertEquals(new BigDecimal("1.20"), rentalAgreement.getDiscount());
    }

    @Test
    public void testCalculateTotal() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> svc.calculateTotal(null),
                "Must create a rental agreement."
        );

        assertThrows(
                Exception.class,
                () -> svc.calculateTotal(rentalAgreement),
                "Must calculate subtotal before total can be calculated."
        );

        rentalAgreement.setSubtotal(new BigDecimal("11.96"));

        assertThrows(
                Exception.class,
                () -> svc.calculateTotal(rentalAgreement),
                "Must calculate discount before total can be calculated."
        );
        rentalAgreement.setDiscount(new BigDecimal("1.20"));

        assertDoesNotThrow(
                () -> svc.calculateTotal(rentalAgreement)
        );

        assertEquals(new BigDecimal("10.76"), rentalAgreement.getTotal());
    }

}
