package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

/**
 * Created by Globons on 4/5/18.
 */
public class EditTextVacioException extends Throwable {

    public EditTextVacioException(String campo) {
        super(String.format("El campo '%s' es obligatorio", campo));
    }
}
