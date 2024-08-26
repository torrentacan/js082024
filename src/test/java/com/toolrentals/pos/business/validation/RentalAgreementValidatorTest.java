package com.toolrentals.pos.business.validation;

import com.toolrentals.pos.common.model.DeWaltJackhammer;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RentalAgreementValidatorTest {

    private final RentalAgreementValidator validator = new RentalAgreementValidator();

    @Test
    public void testValidateRentalAgreementStarted() {
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementStarted(null),
                "Must create a rental agreement."
        );
    }

    @Test
    public void testValidateToolNull() {
        assertThrows(
                Exception.class,
                () -> validator.validateRentableTool(null),
                "Must select a tool."
        );
    }

    @Test
    public void testValidateRentalDays() {
        assertThrows(
                Exception.class,
                () -> validator.validateRentalDays(0),
                "Must rent tool for at least 1 day."
        );
    }

    @Test
    public void testValidateCheckoutDateNull() {
        assertThrows(
                Exception.class,
                () -> validator.validateCheckoutDate(null),
                "Must select a checkout date."
        );
    }

    @Test
    public void testValidateCheckoutDatePast() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 1); // January 1, 2024
        assertThrows(
                Exception.class,
                () -> validator.validateCheckoutDate(cal.getTime()),
                "Checkout cannot be prior to current date."
        );
    }

    @Test
    public void testValidateDueDateCheckoutDateNullAndRentalDays0() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> validator.validateDueDate(rentalAgreement),
                "Must supply checkout date and number of rental days."
        );
    }

    @Test
    public void testValidateDueDateCheckoutDateNull() {
        RentalAgreement rentalAgreement = new RentalAgreement().rentalDays(1);
        assertThrows(
                Exception.class,
                () -> validator.validateDueDate(rentalAgreement),
                "Must supply checkout date and number of rental days."
        );
    }

    @Test
    public void testValidateDueDateRentalDays0() {
        RentalAgreement rentalAgreement = new RentalAgreement().checkoutDate(new Date());
        assertThrows(
                Exception.class,
                () -> validator.validateDueDate(rentalAgreement),
                "Must supply number of rental days."
        );
    }

    @Test
    public void testValidateDiscountPercent() {
        assertThrows(
                Exception.class,
                () -> validator.validateDiscountPercent(null),
                "Percent discount must be between 0 and 100."
        );
    }

    @Test
    public void testValidateDiscountPercentLessThan0() {
        assertThrows(
                Exception.class,
                () -> validator.validateDiscountPercent(-1),
                "Percent discount must be between 0 and 100."
        );
    }

    @Test
    public void testValidateDiscountPercentOver100() {
        assertThrows(
                Exception.class,
                () -> validator.validateDiscountPercent(101),
                "Percent discount must be between 0 and 100."
        );
    }

    @Test
    public void testValidateDiscountCalculationNull() {
        RentalAgreement agreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> validator.validateDiscountCalculation(agreement),
                "Must calculate daily rental charges before discount can be calculated."
        );
    }

    @Test
    public void testValidateDiscountCalculationSize0() {
        RentalAgreement agreement = new RentalAgreement()
                .rentalChargesByDate(new HashMap<>());
        assertThrows(
                Exception.class,
                () -> validator.validateDiscountCalculation(agreement),
                "Must calculate daily rental charges before discount can be calculated."
        );
    }

    @Test
    public void testValidateCalculateTotalMissingDailyCharges() {
        RentalAgreement agreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> validator.validateCalculateTotal(agreement),
                "Must calculate daily rental charges before discount can be calculated."
        );
    }

    @Test
    public void testValidateCalculateTotalMissingCharges() {
        RentalAgreement agreement = new RentalAgreement()
                .rentalChargesByDate(new HashMap<Date, BigDecimal>() {{
                    put(new Date(), BigDecimal.ZERO);
                }});
        assertThrows(
                Exception.class,
                () -> validator.validateCalculateTotal(agreement),
                "Must calculate subtotal before total can be calculated."
        );
    }

    @Test
    public void testValidateCalculateTotalMissingDiscount() {
        RentalAgreement agreement = new RentalAgreement()
                .rentalChargesByDate(new HashMap<Date, BigDecimal>() {{
                    put(new Date(), BigDecimal.ZERO);
                }})
                .subtotal(BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateCalculateTotal(agreement),
                "Must calculate discount before total can be calculated."
        );
    }

    @Test
    public void testValidateDailyRentalCharges() {
        RentalAgreement rentalAgreement = new RentalAgreement();
        assertThrows(
                Exception.class,
                () -> validator.validateDailyRentalCharges(rentalAgreement),
                "Must calculate the daily rates before calculating the subtotal."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteNullAgreement() {
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(null),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementComplete() {
        RentalAgreement agreement = createRentalAgreement(null, null, null, null, null, null, null, null);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingTool() {
        RentalAgreement agreement = createRentalAgreement(null, new HashMap<>(), new Date(), new Date(), 1, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingRentalDays() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), null, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingCheckoutDate() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), null, new Date(), 1, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingDueDate() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), null, 1, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingChargeableDays() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), null, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingSubtotal() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), 1, null, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingPercentDiscount() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), 1, BigDecimal.ZERO, null, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingDiscount() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), 1, BigDecimal.ZERO, 10, null);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    @Test
    public void testValidateRentalAgreementCompleteMissingTotal() {
        RentalAgreement agreement = createRentalAgreement(new DeWaltJackhammer(), new HashMap<>(), new Date(), new Date(), 1, BigDecimal.ZERO, 10, BigDecimal.ZERO);
        assertThrows(
                Exception.class,
                () -> validator.validateRentalAgreementComplete(agreement),
                "Must complete rental agreement."
        );
    }

    private RentalAgreement createRentalAgreement(Tool tool, HashMap<Date, BigDecimal> chargesByDate, Date checkoutDate, Date dueDate, Integer chargeableDays, BigDecimal subtotal, Integer percentDiscount, BigDecimal discount) {
        return new RentalAgreement()
                .tool(tool)
                .rentalChargesByDate(chargesByDate)
                .checkoutDate(checkoutDate)
                .dueDate(dueDate)
                .numberChargeableDays(chargeableDays)
                .subtotal(subtotal)
                .percentDiscount(percentDiscount)
                .discount(discount);
    }
}
