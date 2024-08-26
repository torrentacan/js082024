package com.toolrentals.pos.business.validation;

import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;

import java.util.Calendar;
import java.util.Date;

/**
 * This class holds the functions which are used to validate the rental agreement.
 */
public class RentalAgreementValidator {

    /**
     * Validate that the supplied rental agreement is not null.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to validate.
     * @throws Exception - if the rental agreement is null.
     */
    public void validateRentalAgreementStarted(RentalAgreement rentalAgreement) throws Exception {
        if (rentalAgreement == null) {
            throw new Exception("Must create a rental agreement.");
        }
    }

    /**
     * Validate that the supplied tool is not null.
     *
     * @param tool Tool - the tool to validate.
     * @throws Exception - if the tool is null.
     */
    public void validateRentableTool(Tool tool) throws Exception {
        // the tool must be a valid tool
        if (tool == null) {
            throw new Exception("Must select a tool.");
        }
    }

    /**
     * Validate the rental days for the rental agreement.
     *
     * @param numberOfRentalDays int - the number of rental days for the agreement.
     * @throws Exception - if the number of rental days is less than 1.
     */
    public void validateRentalDays(int numberOfRentalDays) throws Exception {

        // number of days to rent a tool must be greater than 0
        if (numberOfRentalDays < 1) {
            throw new Exception("Must rent tool for at least 1 day.");
        }
    }

    /**
     * Validate the checkout date for the rental agreement.
     *
     * @param checkoutDate Date - the checkout date to validate.
     * @throws Exception - if the checkout date is null or the checkout date is prior to 'today'.
     */
    public void validateCheckoutDate(Date checkoutDate) throws Exception {
        // checkout date cannot be null or prior to 'today'
        if (checkoutDate == null) {
            throw new Exception("Must select a checkout date.");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        if (checkoutDate.before(cal.getTime())) {
            throw new Exception("Checkout cannot be prior to current date.");
        }
    }

    /**
     * Validate that all of the necessary pieces are in place to be able to calculate the due date for the rental
     * agreement.
     *
     * @param rentalAgreement RentalAgreement - the agreement containing the checkout date and rental days.
     * @throws Exception - if the checkout date and/or rental days are not defined.
     */
    public void validateDueDate(RentalAgreement rentalAgreement) throws Exception {
        if (rentalAgreement.getCheckoutDate() == null && rentalAgreement.getRentalDays() == 0) {
            throw new Exception("Must supply checkout date and number of rental days.");
        } else if (rentalAgreement.getCheckoutDate() == null) {
            throw new Exception("Must supply checkout date.");
        } else if (rentalAgreement.getRentalDays() == 0) {
            throw new Exception("Must supply number of rental days.");
        }
    }

    /**
     * Validate the discount percent to be applied to the rental agreement.
     *
     * @param discountPercent Integer - the discount percent to be validated.
     * @throws Exception - if the discount percent is null, less than 0, or greater than 100.
     */
    public void validateDiscountPercent(Integer discountPercent) throws Exception {
        if (discountPercent == null || discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Percent discount must be between 0 and 100.");
        }
    }

    /**
     * Validate that the subtotal has been calculated so that a discount amount can be calculated.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to validate.
     * @throws Exception - if the subtotal has not been defined.
     */
    public void validateDiscountCalculation(RentalAgreement rentalAgreement) throws Exception {
        if (!rentalAgreement.hasSubtotal()) {
            throw new Exception("Must calculate subtotal before discount can be calculated.");
        }
    }

    /**
     * Validate the required fields to be able to calculate a total for the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to validate.
     * @throws Exception - if the subtotal or discount have not been defined.
     */
    public void validateCalculateTotal(RentalAgreement rentalAgreement) throws Exception {
        if (!rentalAgreement.hasSubtotal()) {
            throw new Exception("Must calculate subtotal before total can be calculated.");
        }
        if (!rentalAgreement.hasDiscount()) {
            throw new Exception("Must calculate discount before total can be calculated.");
        }
    }

    /**
     * Validate that the rental agreement has had the daily charges defined.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to validate.
     * @throws Exception - if the daily charges have not been defined.
     */
    public void validateDailyRentalCharges(RentalAgreement rentalAgreement) throws Exception {
        if (!rentalAgreement.hasDailyRentalCharges()) {
            throw new Exception("Must calculate the daily rates before calculating the subtotal.");
        }
    }

    /**
     * Validate that the rental agreement has all of the necessary data present to be able to finalize the checkout and
     * print out the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to validate.
     * @throws Exception - if the rental agreement is missing any of the required data.
     */
    public void validateRentalAgreementComplete(RentalAgreement rentalAgreement) throws Exception {
        if (rentalAgreement == null ||
                !rentalAgreement.hasTool() ||
                !rentalAgreement.hasRentalDays() ||
                !rentalAgreement.hasCheckoutDate() ||
                !rentalAgreement.hasDueDate() ||
                !rentalAgreement.hasNumberChargeableDays() ||
                !rentalAgreement.hasSubtotal() ||
                !rentalAgreement.hasPercentageDiscount() ||
                !rentalAgreement.hasDiscount() ||
                !rentalAgreement.hasTotal()) {
            throw new Exception("Must complete rental agreement.");
        }
    }
}
