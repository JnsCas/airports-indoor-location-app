package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

import java.util.Calendar;

public final class Util {

    public static long differenceMinutes(Calendar calMin, Calendar calMay) {
        return ((calMay.getTimeInMillis() - calMin.getTimeInMillis()) / 1000) / 60;
    }

}
