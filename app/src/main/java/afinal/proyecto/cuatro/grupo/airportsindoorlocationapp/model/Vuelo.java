package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import java.io.UnsupportedEncodingException;

import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.ContrasenasDistintasException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.EditTextVacioException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.exceptions.signin.FormatoEmailInvalidoException;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Security;
import afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util.Validaciones;

/**
 * Created by Globons on 3/5/18.
 */

public class Vuelo {

    private String vue_id;
    private String vue_desc;
    private String vue_origen;
    private String vue_destino;
    private String vue_fecha_hora;
    private String vue_avion;
    private String lgr_id;



    public String getVue_id() {
        return vue_id;
    }

    public void setVue_id(String vue_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_id)) {
            throw new EditTextVacioException();
        }
        this.vue_id = vue_id;
    }


    public String getVue_desc() {
        return vue_desc;
    }

    public void setVue_desc(String vue_desc) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_desc)) {
            throw new EditTextVacioException();
        }
        this.vue_desc = vue_desc;
    }

    public String getVue_origen() {
        return vue_origen;
    }

    public void setVue_origen(String vue_origen) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_origen)) {
            throw new EditTextVacioException();
        }
        this.vue_origen = vue_origen;
    }


    public String getVue_destino() {
        return vue_destino;
    }

    public void setVue_destino(String vue_destino) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_destino)) {
            throw new EditTextVacioException();
        }
        this.vue_destino = vue_destino;
    }


    public String getVue_fecha_hora() { return vue_fecha_hora; }

    public void setVue_fecha_hora(String vue_fecha_hora) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_fecha_hora)) {
            throw new EditTextVacioException();
        }
        this.vue_fecha_hora = vue_fecha_hora;
    }


    public String getVue_avion() { return vue_avion; }

    public void setVue_avion(String vue_avion) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(vue_avion)) {
            throw new EditTextVacioException();
        }
        this.vue_avion = vue_avion;
    }


    public String getLgr_id() { return lgr_id; }

    public void setLgr_id(String lgr_id) throws EditTextVacioException {
        if (Validaciones.isNullOrEmpty(lgr_id)) {
            throw new EditTextVacioException();
        }
        this.lgr_id = lgr_id;
    }
}
