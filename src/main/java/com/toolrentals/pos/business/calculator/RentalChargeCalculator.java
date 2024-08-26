package com.toolrentals.pos.business.calculator;

import com.toolrentals.pos.business.util.DateUtil;
import com.toolrentals.pos.common.model.RentalAgreement;
import com.toolrentals.pos.common.model.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalChargeCalculator {

    private DateUtil dateUtil;

    public RentalChargeCalculator() {
        init();
    }

    private void init() {
        dateUtil = new DateUtil();
    }

    /**
     * Determine all the days within a rental period that are billable and the associated fees and assign it to the
     * rental agreement.
     *
     * @param rentalAgreement RentalAgreement - the agreement to calculate the daily rates and chargeable days for.
     */
    public void calculateDailyRates(RentalAgreement rentalAgreement) {
        Map<Date, BigDecimal> dailyRentalCharges = new HashMap<Date, BigDecimal>();
        Tool tool = rentalAgreement.getTool();

        // setup a Calendar with the start date of the rental
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalAgreement.getCheckoutDate());

        int numberChargeableDays = 0; // number of days of the rental period that will be charged for

        for (int i = 0; i < rentalAgreement.getRentalDays(); i++) {
            if (!tool.isHolidayCharge() && dateUtil.isHoliday(cal)) {
                // don't bill for Independence Day or Labor Day
                dailyRentalCharges.put(cal.getTime(), new BigDecimal(0));
            } else {
                // otherwise, check the day of the week vs whether the charge applies
                switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    // weekends
                    case Calendar.SATURDAY:
                    case Calendar.SUNDAY:
                        if (tool.isWeekendCharge()) {
                            dailyRentalCharges.put(cal.getTime(), tool.getDailyCharge());
                            numberChargeableDays++;
                        } else {
                            dailyRentalCharges.put(cal.getTime(), new BigDecimal(0));
                        }
                        break;
                    // weekdays
                    default:
                        if (tool.isWeekdayCharge()) {
                            dailyRentalCharges.put(cal.getTime(), tool.getDailyCharge());
                            numberChargeableDays++;
                        }
                }
            }
            // increment the Calendar by a day
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        rentalAgreement.setNumberChargeableDays(numberChargeableDays);
        rentalAgreement.setRentalChargesByDate(dailyRentalCharges);
    }

    /**
     * Calculate the subtotal for the rental agreement and assign it to the agreement.
     *
     * @param rentalAgreement RentalAgreement - the agreement to be modified.
     */
    public void calculateSubtotal(RentalAgreement rentalAgreement) {
        BigDecimal subtotal = BigDecimal.ZERO;
        Map<Date, BigDecimal> m = rentalAgreement.getRentalChargesByDate();
        for (Map.Entry<Date, BigDecimal> entry : m.entrySet()) {
            subtotal = subtotal.add(entry.getValue());
        }
        rentalAgreement.setSubtotal(subtotal);
    }

    /**
     * Calculate any discount to be applied to the rental agreement and assign it to the agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     */
    public void calculateDiscount(RentalAgreement rentalAgreement) {
        BigDecimal discountPercent = BigDecimal.ZERO;
        if (rentalAgreement.hasPercentageDiscount()) {
            discountPercent = new BigDecimal(rentalAgreement.getPercentDiscount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        }
        rentalAgreement.setDiscount(rentalAgreement.getSubtotal().multiply(discountPercent).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Calculate the total charge for the rental agreement and assign it to the agreement.
     *
     * @param rentalAgreement RentalAgreement - the rental agreement to be modified.
     */
    public void calculateTotal(RentalAgreement rentalAgreement) {
        BigDecimal finalRate = rentalAgreement.getSubtotal();
        finalRate = finalRate.subtract(rentalAgreement.getDiscount());
        rentalAgreement.setTotal(finalRate);
    }


}
