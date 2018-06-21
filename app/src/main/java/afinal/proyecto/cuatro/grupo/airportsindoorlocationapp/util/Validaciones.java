package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JnsCas on 3/5/18.
 */

public class Validaciones {
    //Email Pattern
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Valida Email con expresiones regulares
     *
     * @param email
     * @return true para Email Valido y falso para Email Invalido
     */
    public static boolean emailIsValidate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    /**
     * Checkea si un String object es nullo
     *
     * @param txt
     * @return true para null o vacio, y falso para no null o vacio
     */
    public static boolean isNullOrEmpty(String txt){
        return txt==null || txt.trim().length() == 0;
    }

}
