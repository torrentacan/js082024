package com.toolrentals.pos.common.model;

import com.toolrentals.pos.common.enums.Brand;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.enums.ToolType;

import java.math.BigDecimal;

/**
 * This class represents a DeWalt Jackhammer Tool.
 */
public class DeWaltJackhammer extends Tool {
    public DeWaltJackhammer() {
        super(ToolCode.JAKD, ToolType.JACKHAMMER, Brand.DEWALT, new BigDecimal("2.99"), true, false, false);
    }
}
