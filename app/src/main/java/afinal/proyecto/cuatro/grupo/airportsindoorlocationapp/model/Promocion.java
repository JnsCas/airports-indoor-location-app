package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

/**
 * Created by Globons on 3/5/18.
 */

public class Promocion {

    private String pro_id;
    private String pro_URL;
    private String pro_distancia;
    private String bea_id;


    public String getPro_id() { return pro_id; }

    public void setPro_id(String pro_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(pro_id)) {
            throw new EditTextVacioException();
        }
        this.pro_id = pro_id;
    }


    public String getPro_URL() {
        return pro_URL;
    }

    public void setPro_URL(String pro_URL) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(pro_URL)) {
            throw new EditTextVacioException();
        }
        this.pro_URL = pro_URL;
    }


    public String getPro_distancia() { return pro_distancia; }

    public void setPro_distancia(String pro_distancia) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(pro_distancia)) {
            throw new EditTextVacioException();
        }
        this.pro_distancia = pro_distancia;
    }


    public String getBea_id() { return bea_id; }

    public void setBea_id(String bea_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(bea_id)) {
            throw new EditTextVacioException();
        }
        this.bea_id = bea_id;
    }

}
