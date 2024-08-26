package com.toolrentals.pos.service;

import com.toolrentals.pos.business.calculator.RentalChargeCalculator;
import com.toolrentals.pos.business.util.DateUtil;
import com.toolrentals.pos.business.validation.RentalAgreementValidator;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.model.*;

import java.util.Date;

/**
 * Provided implementation for the rental agreement service.
 */
public class RentalAgreementSvcImpl implements RentalAgreementSvc {

    private RentalAgreementValidator validation;
    private RentalChargeCalculator rentalChargeCalculator;
    private DateUtil dateUtil;

    public RentalAgreementSvcImpl() {
        init();
    }

    private void init() {
        validation = new RentalAgreementValidator();
        rentalChargeCalculator = new RentalChargeCalculator();
        dateUtil = new DateUtil();
    }

    public RentalAgreement createRentalAgreement() {
        return new RentalAgreement();
    }

    public Tool findTool(ToolCode toolCode) {
        return switch (toolCode) {
            case ToolCode.CHNS -> new StihlChainsaw();
            case ToolCode.JAKD -> new DeWaltJackhammer();
            case ToolCode.JAKR -> new RidgidJackhammer();
            case ToolCode.LADW -> new WernerLadder();
        };
    }

    public void assignToolToRentalAgreement(RentalAgreement rentalAgreement, Tool tool) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateRentableTool(tool);
        rentalAgreement.setTool(tool);
    }

    public void assignRentalDaysToRentalAgreement(RentalAgreement rentalAgreement, int numberOfRentalDays) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateRentalDays(numberOfRentalDays);
        rentalAgreement.setRentalDays(numberOfRentalDays);
    }

    public void assignCheckoutDateToRentalAgreement(RentalAgreement rentalAgreement, Date checkoutDate) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateCheckoutDate(checkoutDate);
        rentalAgreement.setCheckoutDate(checkoutDate);
    }

    public void calculateDueDate(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateDueDate(rentalAgreement);
        rentalAgreement.setDueDate(dateUtil.addDaysToDate(rentalAgreement.getCheckoutDate(), rentalAgreement.getRentalDays()));
    }

    public void calculateDailyRentalCharges(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateRentableTool(rentalAgreement.getTool());
        validation.validateDueDate(rentalAgreement);
        rentalChargeCalculator.calculateDailyRates(rentalAgreement);
    }

    public void calculateSubtotal(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateDailyRentalCharges(rentalAgreement);
        rentalChargeCalculator.calculateSubtotal(rentalAgreement);
    }

    public void assignPercentDiscount(RentalAgreement rentalAgreement, int percentDiscount) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateDiscountPercent(percentDiscount);
        rentalAgreement.setPercentDiscount(percentDiscount);
    }

    public void calculateDiscountAmount(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateDiscountCalculation(rentalAgreement);
        rentalChargeCalculator.calculateDiscount(rentalAgreement);
    }

    public void calculateTotal(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementStarted(rentalAgreement);
        validation.validateCalculateTotal(rentalAgreement);
        rentalChargeCalculator.calculateTotal(rentalAgreement);
    }

    public void printRentalAgreement(RentalAgreement rentalAgreement) throws Exception {
        validation.validateRentalAgreementComplete(rentalAgreement);
        System.out.printf("Tool code: %s%n", rentalAgreement.getTool().getToolCode());
        System.out.printf("Tool type: %s%n", rentalAgreement.getTool().getToolType());
        System.out.printf("Tool brand: %s%n", rentalAgreement.getTool().getBrand());
        System.out.printf("Rental days: %,d%n", rentalAgreement.getRentalDays());
        System.out.printf("Checkout date: %s%n", dateUtil.formatDateMMDDYY(rentalAgreement.getCheckoutDate()));
        System.out.printf("Due date: %s%n", dateUtil.formatDateMMDDYY(rentalAgreement.getDueDate()));
        System.out.printf("Daily rental charge: $%,.2f%n", rentalAgreement.getTool().getDailyCharge());
        System.out.printf("Charge days: %d%n", rentalAgreement.getNumberChargeableDays());
        System.out.printf("Pre-discount charge: $%,.2f%n", rentalAgreement.getSubtotal());
        System.out.printf("Discount percent: %d%%%n", rentalAgreement.getPercentDiscount());
        System.out.printf("Discount amount: $%,.2f%n", rentalAgreement.getDiscount());
        System.out.printf("Final charge: $%,.2f%n", rentalAgreement.getTotal());
    }

}
