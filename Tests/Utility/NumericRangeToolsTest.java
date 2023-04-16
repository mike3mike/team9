package Utility;

import org.junit.Test;

public class NumericRangeToolsTest {

    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     * @requires 0 <= percentage <= 100;
     * @ensures \result = true;
     *          }
     */

    @Test
    public void testIsValidPercentageValidZero() {
        int percentage = 0;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == true;
    }

    @Test
    public void testIsValidPercentageValidFifty() {
        int percentage = 50;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == true;
    }

    @Test
    public void testIsValidPercentageValidHundred() {
        int percentage = 100;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == true;
    }

    /*
     * @subcontract value out of range low {
     * 
     * @requires percentage < 0;
     * 
     * @ensures \result = false;
     * }
     */

    @Test
    public void testIsValidPercentageOutOfRangeLowNegativeOne() {
        int percentage = -1;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == false;
    }

    @Test
    public void testIsValidPercentageOutOfRangeLowNegativeHundred() {
        int percentage = -100;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == false;
    }

    /*
     * @subcontract value out of range high {
     * 
     * @requires percentage > 100;
     * 
     * @signals \result = false;
     * }
     * 
     */

    @Test
    public void testIsValidPercentageOutOfRangeHighOneHundredAndOne() {
        int percentage = 101;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == false;
    }

    @Test
    public void testIsValidPercentageOutOfRangeHighOneHundredAndHundred() {
        int percentage = 200;
        boolean result = NumericRangeTools.isValidPercentage(percentage);
        assert result == false;
    }

}
