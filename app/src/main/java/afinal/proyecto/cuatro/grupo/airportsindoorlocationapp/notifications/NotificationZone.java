package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.notifications;

import com.estimote.proximity_sdk.api.ProximityZoneContext;

public class NotificationZone {

    private String tag;
    private Integer id;
    private String title;
    private String message;
    private String link;

    public NotificationZone(String tag, ProximityZoneContext proximityContext) {
        this.tag = tag;
        this.id = Integer.parseInt(proximityContext.getAttachments().get(tag + "/id"));
        this.title = proximityContext.getAttachments().get(tag + "/title");
        this.message = proximityContext.getAttachments().get(tag + "/message");
        this.link = proximityContext.getAttachments().get(tag + "/link");
    }

    String getTitle() {
        return title;
    }

    String getMessage() {
        return message;
    }

    Integer getId() {
        return id;
    }

    String getLink() {
        return link;
    }
}
