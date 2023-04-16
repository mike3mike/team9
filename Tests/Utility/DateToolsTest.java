package Utility;

import org.junit.Test;

public class DateToolsTest {
    /**
     * @desc Validates is a given date in the form of day, month and year is valid.
     * 
     * @subcontract 31 days in month {
     * @requires (month == 1 || month == 3 || month == 5 || month == 7 ||
     *           month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
     * @ensures \result = true;
     *          }
     */

    @Test
    public void testValidateDate31DaysInMonthJanuary() {
        int day = 31;
        int month = 1;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthMarch() {
        int day = 31;
        int month = 3;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthMay() {
        int day = 31;
        int month = 5;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthJuly() {
        int day = 31;
        int month = 7;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthAugust() {
        int day = 31;
        int month = 8;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthOctober() {
        int day = 31;
        int month = 10;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate31DaysInMonthDecember() {
        int day = 31;
        int month = 12;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    /*
     * @subcontract 30 days in month {
     * 
     * @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
     * 1 <= day <= 30;
     * 
     * @ensures \result = true;
     * }
     */

    @Test
    public void testValidateDate30DaysInMonthApril() {
        int day = 30;
        int month = 4;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate30DaysInMonthJune() {
        int day = 30;
        int month = 6;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;

    }

    @Test
    public void testValidateDate30DaysInMonthSeptember() {
        int day = 30;
        int month = 9;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    @Test
    public void testValidateDate30DaysInMonthNovember() {
        int day = 30;
        int month = 11;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    /*
     * @subcontract 29 days in month {
     * 
     * @requires month == 2 && 1 <= day <= 29 &&
     * (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
     * 
     * @ensures \result = true;
     * }
     */

    @Test
    public void testValidateDate29DaysInMonthFebruaryLeapYear() {
        int day = 29;
        int month = 2;
        int year = 2020;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    /*
     * @subcontract 28 days in month {
     * 
     * @requires month == 2 && 1 <= day <= 28 &&
     * (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
     * 
     * @ensures \result = true;
     * }
     */

    @Test
    public void testValidateDate28DaysInMonthFebruaryNotLeapYear() {
        int day = 28;
        int month = 2;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == true;
    }

    /*
     * @subcontract all other cases {
     * 
     * @requires no other accepting precondition;
     * 
     * @ensures \result = false;
     * }
     * 
     */

    @Test
    public void testValidateDateAllOtherCasesDateZero() {
        int day = 0;
        int month = 0;
        int year = 0;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == false;
    }

    @Test
    public void testValidateDateAllOtherCases32ndOfMonth13() {
        int day = 32;
        int month = 13;
        int year = 2023;
        boolean result = DateTools.validateDate(day, month, year);
        assert result == false;
    }
}
