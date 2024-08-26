package com.toolrentals.pos.common.model;

import com.toolrentals.pos.common.enums.Brand;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.enums.ToolType;

import java.math.BigDecimal;

/**
 * This class represents a Stihl Chainsaw.
 */
public class StihlChainsaw extends Tool {
    public StihlChainsaw() {
        super(ToolCode.CHNS, ToolType.CHAINSAW, Brand.STIHL, new BigDecimal("1.49"), true, false, true);
    }
}
