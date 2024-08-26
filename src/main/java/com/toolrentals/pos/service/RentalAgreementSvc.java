package com.toolrentals.pos.service;

import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;

import java.util.Date;

/**
 * Interface for managing rental agreements. Provided implementation {@link RentalAgreementSvcImpl}
 */
public interface RentalAgreementSvc {

    /**
     * Create a new, blank rental agreement.
     *
     * @return RentalAgreement - new rental agreement to fill out.
     */
    public RentalAgreement createRentalAgreement();

    /**
     * Find the correct {@Link Tool} based on the supplied {@Link ToolCode}.
     *
     * @param toolCode ToolCode - the ToolCode for the tool to find.
     * @return Tool - the correct type of tool.
     */
    public Tool findTool(ToolCode toolCode);

    /**
     * Assign a tool to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to assign the rentable tool to.
     * @param tool            Tool - the rentable tool to be assigned to the rental agreement.
     * @throws Exception - if a rental agreement or tool is not supplied.
     */
    public void assignToolToRentalAgreement(RentalAgreement rentalAgreement, Tool tool) throws Exception;

    /**
     * Assign the number of days for the rental to the rental agreement.
     *
     * @param rentalAgreement    RentalAgreement - the rental agreement to modify.
     * @param numberOfRentalDays int - the number of days for the rental.
     * @throws Exception - if a rental agreement is not supplied or if the number of days for the rental agreement is
     *                   less than 1.
     */
    public void assignRentalDaysToRentalAgreement(RentalAgreement rentalAgreement, int numberOfRentalDays) throws Exception;

    /**
     * Assign the rental tool checkout date to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to modify.
     * @param checkoutDate    Date - the date the customer wishes to begin the rental of the tool.
     * @throws Exception - if a rental agreement is not supplied or if the checkout date is not supplied or prior to
     *                   'today'.
     */
    public void assignCheckoutDateToRentalAgreement(RentalAgreement rentalAgreement, Date checkoutDate) throws Exception;

    /**
     * Calculates and assigns the due date of the rental tool to the rental agreement based on the checkout date and the
     * rental days assigned to the agreement and then applies this to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to modify.
     * @throws Exception - if a rental agreement is not supplied or if the checkout date and/or rental days are not
     *                   defined.
     */
    public void calculateDueDate(RentalAgreement rentalAgreement) throws Exception;

    /**
     * Calculates and assigns the charges for the rental for each day of the rental period to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     * @throws Exception - if the rental agreement is not supplied, the tool has not been assigned, or the checkout date
     *                   and rental days have not been defined.
     */
    public void calculateDailyRentalCharges(RentalAgreement rentalAgreement) throws Exception;

    /**
     * Calculate the subtotal for the rental agreement and assign it to the agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     * @throws Exception - if the rental agreement is not supplied or if the daily rental charges have not been
     *                   calculated.
     */
    public void calculateSubtotal(RentalAgreement rentalAgreement) throws Exception;

    /**
     * Assign a discount percentage to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     * @param percentDiscount int - the discount percentage to be applied. Integer value between 0 and 100.
     * @throws Exception - if the rental agreement is not supplied or if the discount percentage is invalid.
     */
    public void assignPercentDiscount(RentalAgreement rentalAgreement, int percentDiscount) throws Exception;

    /**
     * Calculate and apply the discount amount to the rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     * @throws Exception - if the rental agreement is not supplied or if the subtotal has not been calculated.
     */
    public void calculateDiscountAmount(RentalAgreement rentalAgreement) throws Exception;

    /**
     * Calculate the total for the rental agreement and apply it to the agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     * @throws Exception - if the rental agreement is not supplied or if the subtotal or discount have not been
     *                   calculated.
     */
    public void calculateTotal(RentalAgreement rentalAgreement) throws Exception;

    /**
     * Print out the rental agreement information as part of the checkout process.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be printed out.
     * @throws Exception - if any of the required data in the rental agreement has not been completed.
     */
    public void printRentalAgreement(RentalAgreement rentalAgreement) throws Exception;

}
