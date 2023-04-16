package Utility;

import org.junit.Test;

public class PostalCodeTest {

    /**
     * @desc Formats the input postal code to a uniform output in the form
     *       nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital
     *       letters.
     *       Spaces before and after the input string are trimmed.
     * 
     * @subcontract null postalCode {
     * @requires postalCode == null;
     * @signals (NullPointerException) postalCode == null;
     *          }
     * 
     */

    @Test(expected = NullPointerException.class)
    public void testFormatPostalCodeNull() {
        String postalCode = null;
        PostalCode.formatPostalCode(postalCode);
    }

    /*
     * @subcontract valid postalCode {
     * 
     * @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     * Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     * postalCode.trim().substring(4).trim().length == 2 &&
     * 'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z'
     * &&
     * 'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     * 
     * @ensures \result = postalCode.trim().substring(0, 4) + " " +
     * postalCode.trim().substring(4).trim().toUpperCase()
     * }
     */

    @Test
    public void testFormatPostalCodeValidAllLowerCase() {
        String postalCode = "1234 ab";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValidAllUpperCase() {
        String postalCode = "1234 AB";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValidWithSpaceAtEnd() {
        String postalCode = "1234 AB ";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValidWithSpaceInfront() {
        String postalCode = " 1234 AB";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValidWith2SpacesInbetween() {
        String postalCode = "1234  AB";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValidWithNoSpaces() {
        String postalCode = "1234AB";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValid1stLowerCase2ndUpperCase() {
        String postalCode = "1234 aB";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    @Test
    public void testFormatPostalCodeValid1stUpperCase2ndLowerCase() {
        String postalCode = "1234 Ab";
        String result = PostalCode.formatPostalCode(postalCode);
        assert result.equals("1234 AB");
    }

    /*
     * @subcontract invalid postalCode {
     * 
     * @requires no other valid precondition;
     * 
     * @signals (IllegalArgumentException);
     * }
     * 
     */

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalid1stDigitNotNumeric() {
        String postalCode = "a234 AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalid2ndDigitNotNumeric() {
        String postalCode = "1a34 AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalid3rdDigitNotNumeric() {
        String postalCode = "12a4 AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalid4thDigitNotNumeric() {
        String postalCode = "123a AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalidDigitInsteadOf1stChar() {
        String postalCode = "1234 5B";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalidDigitInsteadOf2ndChar() {
        String postalCode = "1234 A6";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalidOnly3Digits() {
        String postalCode = "123 AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalid5Digits() {
        String postalCode = "1234 5AB";
        PostalCode.formatPostalCode(postalCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatPostalCodeInvalidOnly1Char() {
        String postalCode = "1234 A";
        PostalCode.formatPostalCode(postalCode);
    }

}
