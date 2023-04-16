package Utility;

import org.junit.Test;

public class MailsToolsTest {
    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false;
     *          }
     */
    @Test
    public void testValidateMailAddressNoMailboxPartOnlyDomain() {
        String mailAddress = "@gmail.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == false;
    }

    @Test
    public void testValidateMailAddressNoMailboxPartNoAtSymbol() {
        String mailAddress = "gmail.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == false;
    }

    @Test
    public void testValidateMailAddressNoMailboxPartNoDomain() {
        String mailAddress = "johndoe@gmail.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == true;
    }

    /*
     * @subcontract subdomain-tld delimiter {
     * 
     * @requires !mailAddress.contains("@") ||
     * c;
     * 
     * @ensures \result = false;
     * }
     */

    @Test
    public void testValidateMailAddressSubdomainTldDelimiter() {
        // "." moet "//." zijn
        String mailAddress = "johndoe@subdomain.gmail.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == false;
    }

    /*
     * @subcontract no subdomain part {
     * 
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[1].split(".")[0].length < 1;
     * 
     * @ensures \result = false;
     * }
     */

    @Test
    public void testValidateMailAddressNoSubdomainPart() {
        String mailAddress = "johndoe@.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == false;
    }
    /*
     * @subcontract no tld part {
     * 
     * @requires !mailAddress.contains("@") ||
     * mailAddress.split("@")[1].split(".")[1].length < 1;
     * 
     * @ensures \result = false;
     * }
     */

    @Test
    public void testValidateMailAddressNoTldnPart() {
        String mailAddress = "johndoe@gmail.";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == false;
    }

    /*
     * @subcontract valid email {
     * 
     * @requires no other precondition
     * 
     * @ensures \result = true;
     * }
     * 
     */

    @Test
    public void testValidateMailAddressValid() {
        String mailAddress = "johndoe@gmail.com";
        boolean result = MailsTools.validateMailAddress(mailAddress);
        assert result == true;
    }
}
