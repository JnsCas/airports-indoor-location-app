package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin;

/**
 * Created by Globons on 4/5/18.
 */
public class EditTextVacioException extends Throwable {

    public EditTextVacioException() {
        super(String.format("Este campo es obligatorio"));
    }
}
