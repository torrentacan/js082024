package com.toolrentals.pos.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * This class represents a rental agreement and all of the required pieces of information about the agreement.
 */
public class RentalAgreement implements Serializable {

    Tool tool;
    Integer rentalDays;
    Date checkoutDate;
    Date dueDate;
    Map<Date, BigDecimal> rentalChargesByDate;
    BigDecimal subtotal;
    Integer percentDiscount;
    BigDecimal discount;
    BigDecimal total;
    Integer numberChargeableDays;

    public RentalAgreement() {
    }

    public boolean hasTool() {
        return tool != null;
    }

    public boolean hasCheckoutDate() {
        return checkoutDate != null;
    }

    public boolean hasRentalDays() {
        return rentalDays != null;
    }

    public boolean hasDueDate() {
        return dueDate != null;
    }

    public boolean hasDailyRentalCharges() {
        return rentalChargesByDate != null && !rentalChargesByDate.isEmpty();
    }

    public boolean hasNumberChargeableDays() {
        return numberChargeableDays != null;
    }

    public boolean hasSubtotal() {
        return subtotal != null;
    }

    public boolean hasPercentageDiscount() {
        return percentDiscount != null;
    }

    public boolean hasDiscount() {
        return discount != null;
    }

    public boolean hasTotal() {
        return total != null;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public RentalAgreement tool(Tool tool) {
        this.setTool(tool);
        return this;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public RentalAgreement rentalDays(Integer rentalDays) {
        this.setRentalDays(rentalDays);
        return this;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public RentalAgreement checkoutDate(Date checkoutDate) {
        this.setCheckoutDate(checkoutDate);
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public RentalAgreement dueDate(Date dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public Map<Date, BigDecimal> getRentalChargesByDate() {
        return rentalChargesByDate;
    }

    public void setRentalChargesByDate(Map<Date, BigDecimal> rentalChargesByDate) {
        this.rentalChargesByDate = rentalChargesByDate;
    }

    public RentalAgreement rentalChargesByDate(Map<Date, BigDecimal> rentalChargesByDate) {
        this.setRentalChargesByDate(rentalChargesByDate);
        return this;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public RentalAgreement subtotal(BigDecimal subtotal) {
        this.setSubtotal(subtotal);
        return this;
    }

    public Integer getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Integer percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public RentalAgreement percentDiscount(Integer percentDiscount) {
        this.setPercentDiscount(percentDiscount);
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public RentalAgreement discount(BigDecimal discount) {
        this.setDiscount(discount);
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public RentalAgreement total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public Integer getNumberChargeableDays() {
        return numberChargeableDays;
    }

    public void setNumberChargeableDays(Integer numberChargeableDays) {
        this.numberChargeableDays = numberChargeableDays;
    }

    public RentalAgreement numberChargeableDays(Integer numberChargeableDays) {
        this.setNumberChargeableDays(numberChargeableDays);
        return this;
    }
}
