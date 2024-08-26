package com.toolrentals.pos.common.model;

import com.toolrentals.pos.common.enums.Brand;
import com.toolrentals.pos.common.enums.ToolCode;
import com.toolrentals.pos.common.enums.ToolType;

import java.math.BigDecimal;

/**
 * This class represents a Werner Ladder.
 */
public class WernerLadder extends Tool {
    public WernerLadder() {
        super(ToolCode.LADW, ToolType.LADDER, Brand.WERNER, new BigDecimal("1.99"), true, true, false);
    }
}
