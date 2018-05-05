package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin;

/**
 * Created by JnsCas on 3/5/18.
 */
public class ContrasenasDistintasException extends Throwable {

    public static final String DESCRIPCION = "Las contraseñas ingresadas no coinciden.";

    public ContrasenasDistintasException() {
        super(DESCRIPCION);
    }

}
