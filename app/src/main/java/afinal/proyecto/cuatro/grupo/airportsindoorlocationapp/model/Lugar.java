package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

/**
 * Created by Globons on 3/5/18.
 */

public class Lugar {

    private String lgr_id;
    private String lgr_desc;
    private String lgr_posicion;
    private String lgr_tipo;


    public String getLgr_id() { return lgr_id; }

    public void setLgr_id(String lgr_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_id)) {
            throw new EditTextVacioException();
        }
        this.lgr_id = lgr_id;
    }


    public String getLgr_desc() {
        return lgr_desc;
    }

    public void setLgr_desc(String lgr_desc) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_desc)) {
            throw new EditTextVacioException();
        }
        this.lgr_desc = lgr_desc;
    }

    public String getLgr_posicion() {
        return lgr_posicion;
    }

    public void setLgr_posicion(String lgr_posicion) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_posicion)) {
            throw new EditTextVacioException();
        }
        this.lgr_posicion = lgr_posicion;
    }


    public String getLgr_tipo() {
        return lgr_tipo;
    }

    public void setLgr_tipo(String lgr_tipo) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_tipo)) {
            throw new EditTextVacioException();
        }
        this.lgr_tipo = lgr_tipo;
    }

}
