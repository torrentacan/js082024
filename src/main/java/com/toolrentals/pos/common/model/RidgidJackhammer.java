package com.toolrentals.pos.common.model;

import com.toolrentals.pos.common.enums.Brand;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.enums.ToolType;

import java.math.BigDecimal;

/**
 * This class represents a Ridgid Jackhammer.
 */
public class RidgidJackhammer extends Tool {
    public RidgidJackhammer() {
        super(ToolCode.JAKR, ToolType.JACKHAMMER, Brand.RIDGID, new BigDecimal("2.99"), true, false, false);
    }
}
