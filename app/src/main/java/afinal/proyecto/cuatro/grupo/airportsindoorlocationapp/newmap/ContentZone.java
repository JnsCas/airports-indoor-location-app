package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

import com.estimote.proximity_sdk.api.ProximityZoneContext;

import java.util.Arrays;
import java.util.List;

public class ContentZone {

    private List<String> pos;
    private String tag;
    private String code;
    private Boolean isComingIn;

    public ContentZone(String tag, Boolean isComingIn, ProximityZoneContext proximityContext) {
        this.tag = tag;
        this.isComingIn = isComingIn;
        this.code = proximityContext.getAttachments().get(tag + "/code");
        String aux = proximityContext.getAttachments().get(tag + "/pos");
        this.pos = Arrays.asList(aux.split("\\s*;\\s*"));
    }

    List<String> getPos() {
        return pos;
    }

    String getTag() {
        return tag;
    }

    String getCode() {
        return code;
    }

    Boolean getIsComingIn() {
        return isComingIn;
    }

    void setIsComingIn(Boolean action) {
        this.isComingIn = action;
    }
}
