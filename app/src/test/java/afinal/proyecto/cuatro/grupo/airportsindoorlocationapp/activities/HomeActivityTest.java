package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.activities;

import org.junit.Test;

import java.util.Calendar;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Util;

import static org.junit.Assert.assertEquals;

public class HomeActivityTest {

    @Test
    public void differenceMinutes() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR, 21);
        cal1.set(Calendar.MINUTE, 55);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR, 22);
        cal2.set(Calendar.MINUTE, 5);

        long differenceMinutes = Util.differenceMinutes(cal1, cal2);
        assertEquals(10, differenceMinutes);

        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.HOUR, 23);
        cal3.set(Calendar.MINUTE, 50);

        differenceMinutes = Util.differenceMinutes(cal1, cal3);
        assertEquals(115, differenceMinutes);
    }
}