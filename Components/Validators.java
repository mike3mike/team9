package Components;

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

    public static boolean URLValid(String URL) {
        String Regex = "[(http(s)?):\\/\\/(\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

        Pattern pat = Pattern.compile(Regex);
        if (URL == null)
            return false;
        return pat.matcher(URL).matches();
    }

    public static boolean progressValid(String progress) {
        String Regex = "^[1-9][0-9]?$|^100$";

        Pattern pat = Pattern.compile(Regex);
        if (progress == null)
            return false;
        return pat.matcher(progress).matches();
    }

    public static boolean dateValid(String progress) {
        String Regex = "^\\d\\d-\\d\\d-\\d\\d\\d\\d";

        Pattern pat = Pattern.compile(Regex);
        if (progress == null)
            return false;
        return pat.matcher(progress).matches();
    }

}
