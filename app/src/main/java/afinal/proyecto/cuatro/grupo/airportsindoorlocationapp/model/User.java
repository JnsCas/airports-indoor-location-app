package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.ContrasenasDistintasException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.FormatoEmailInvalidoException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Security;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

/**
 * Created by Globons on 3/5/18.
 */

public class User {

    private int id;
    private String nombre;
    private String email;
    private String contrasena;
    private List<Vuelo> vuelos;

    public User() {

    }

    public User(int id, String nombre, String email, String contrasena, List<Vuelo> vuelos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.vuelos = vuelos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(nombre)) {
            throw new EditTextVacioException();
        }
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EditTextVacioException, FormatoEmailInvalidoException {
        if (Validaciones.isNullOrEmpty(email)) {
            throw new EditTextVacioException();
        }
        if (!Validaciones.emailIsValidate(email)) {
            throw new FormatoEmailInvalidoException();
        }
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena, String confirmarContrasena) throws ContrasenasDistintasException, UnsupportedEncodingException {
        if (contrasena.equals(confirmarContrasena)) {
            this.contrasena = Security.getSHA512SecurePassword(contrasena);
        } else {
            throw new ContrasenasDistintasException();
        }
    }

    public void setContrasena(String contrasena) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(contrasena)) {
            throw new EditTextVacioException();
        }
        this.contrasena = contrasena;
    }
}
