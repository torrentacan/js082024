package com.toolrentals.pos;

import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;
import com.toolrentals.pos.service.RentalAgreementSvc;
import com.toolrentals.pos.service.RentalAgreementSvcImpl;

import java.util.Date;

/**
 * Process for checking out a customer and creating the rental agreement for renting a tool.
 */
public class CheckoutProcess {

    RentalAgreementSvc rentalAgreementSvc;

    public CheckoutProcess() {
        rentalAgreementSvc = new RentalAgreementSvcImpl();
    }

    /**
     * Checkout process, creating a rental agreement for a customer renting a tool.
     *
     * @param toolCode        ToolCode - enum value representing the code of the tool that the customer desires to rent
     * @param rentalDays      int - the number of days that the customer wishes to rent the tool for
     * @param checkoutDate    Date - the date that the customer wishes to start renting the tool
     * @param discountPercent int - the discount amount (whole number from 0 - 100) that the sales representative is
     *                        applying to the rental agreement
     * @throws Exception - if any errors occur during the checkout process or an invalid value has been supplied.
     */
    public void checkout(ToolCode toolCode, int rentalDays, Date checkoutDate, int discountPercent) throws Exception {
        RentalAgreement rentalAgreement = rentalAgreementSvc.createRentalAgreement();
        Tool tool = rentalAgreementSvc.findTool(toolCode);
        rentalAgreementSvc.assignToolToRentalAgreement(rentalAgreement, tool);
        rentalAgreementSvc.assignRentalDaysToRentalAgreement(rentalAgreement, rentalDays);
        rentalAgreementSvc.assignCheckoutDateToRentalAgreement(rentalAgreement, checkoutDate);
        rentalAgreementSvc.calculateDueDate(rentalAgreement);
        rentalAgreementSvc.calculateDailyRentalCharges(rentalAgreement);
        rentalAgreementSvc.assignPercentDiscount(rentalAgreement, discountPercent);
        rentalAgreementSvc.calculateSubtotal(rentalAgreement);
        rentalAgreementSvc.calculateDiscountAmount(rentalAgreement);
        rentalAgreementSvc.calculateTotal(rentalAgreement);
        rentalAgreementSvc.printRentalAgreement(rentalAgreement);
    }
}
