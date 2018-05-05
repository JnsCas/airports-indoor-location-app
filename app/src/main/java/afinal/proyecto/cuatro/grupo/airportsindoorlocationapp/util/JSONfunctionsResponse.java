package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.util;

import org.json.JSONObject;


public class JSONfunctionsResponse {

    private Integer status;
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
