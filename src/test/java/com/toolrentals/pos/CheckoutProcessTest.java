package com.toolrentals.pos;

import com.toolrentals.pos.common.enums.ToolCode;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutProcessTest {

    private CheckoutProcess checkoutProcess = new CheckoutProcess();

    @Test
    public void testCheckoutProcessNoTool() {
        // tool not supplied
        assertThrows(
                Exception.class,
                () -> checkoutProcess.checkout(null, 7000, new Date(), 0),
                "Checkout cannot be prior to current date."
        );
    }

    @Test
    public void testCheckoutProcessDateNotSupplied(){
        // checkout date not supplied
        assertThrows(
                Exception.class,
                () -> checkoutProcess.checkout(ToolCode.JAKD, 7000, null, 0),
                "Checkout cannot be prior to current date."
        );
    }

    @Test
    public void testCheckoutProcessDateBeforeToday(){
        // set the calendar to yesterday
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, -1);
        assertThrows(
                Exception.class,
                () -> checkoutProcess.checkout(ToolCode.JAKD, 7000, cal.getTime(), 0),
                "Checkout cannot be prior to current date."
        );
    }

    @Test
    public void testCheckoutProcessNoErrors() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.YEAR, 2025);
        assertDoesNotThrow(
                () -> checkoutProcess.checkout(ToolCode.JAKD, 7000, cal.getTime(), 0)
        );
    }

}
