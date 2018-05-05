package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin;

/**
 * Created by JnsCas on 4/5/18.
 */
public class FormatoEmailInvalidoException extends Throwable {

    public static final String DESCRIPCION = "El formato de email es inválido.";

    public FormatoEmailInvalidoException() {
        super(DESCRIPCION);
    }
}
