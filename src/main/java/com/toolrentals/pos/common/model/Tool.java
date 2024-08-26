package com.toolrentals.pos.common.model;

import com.toolrentals.pos.common.enums.Brand;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.enums.ToolType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class defines the structure of a tool available for rental.
 */
public abstract class Tool implements Serializable {

    private ToolCode toolCode;
    private ToolType toolType;
    private Brand brand;
    private BigDecimal dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public Tool() {
    }

    public Tool(ToolCode toolCode, ToolType toolType, Brand brand, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public ToolCode getToolCode() {
        return toolCode;
    }

    public void setToolCode(ToolCode toolCode) {
        this.toolCode = toolCode;
    }

    public boolean hasToolCode() {
        return toolCode != null;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public boolean hasToolType() {
        return toolType != null;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean hasBrand() {
        return brand != null;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean hasDailyCharge() {
        return dailyCharge != null;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}
