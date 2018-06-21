package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

import android.widget.EditText;

import java.util.List;

public class ExceptionUtil {

    public static void setearErrorEditTextsCamposObligatorios(List<EditText> editTexts, String messageException) {
        for (EditText et: editTexts) {
            if (Validaciones.isNullOrEmpty(et.getText().toString())) {
                et.setError(messageException);
            }
        }
    }

}
