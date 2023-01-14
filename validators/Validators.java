package validators;

import java.util.regex.Pattern;

public class Validators {

    /**
     * @param email
     * @return
     */
    public static boolean emailValid(String email) {
        String emailRegex = "^(.+)@(.+)$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean postcodeValid(String Postalcode) {
        String Regex = "[1-9]\\d\\d\\d\\s[A-Z][A-Z]";

        Pattern pat = Pattern.compile(Regex);
        if (Postalcode == null)
            return false;
        return pat.matcher(Postalcode).matches();
    }

}
