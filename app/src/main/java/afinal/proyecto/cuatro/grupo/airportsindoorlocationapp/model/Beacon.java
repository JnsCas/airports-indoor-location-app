package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

/**
 * Created by Globons on 3/5/18.
 */

public class Beacon {

    private String bea_id;
    private String bea_tipo;
    private String lgr_id;


    public String getBea_id() { return bea_id; }

    public void setBea_id(String bea_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(bea_id)) {
            throw new EditTextVacioException();
        }
        this.bea_id = bea_id;
    }


    public String getBea_tipo() {
        return bea_tipo;
    }

    public void setBea_tipo(String bea_tipo) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(bea_tipo)) {
            throw new EditTextVacioException();
        }
        this.bea_tipo = bea_tipo;
    }


    public String getLgr_id() { return lgr_id; }

    public void setLgr_id(String lgr_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_id)) {
            throw new EditTextVacioException();
        }
        this.lgr_id = lgr_id;
    }

}
