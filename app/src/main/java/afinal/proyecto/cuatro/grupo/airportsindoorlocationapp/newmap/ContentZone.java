package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.newmap;

class ContentZone {

    private Integer pos;
    private String posName;
    private String tag;
    private Boolean isComingIn;

    ContentZone(String pos, String posName, String tag, Boolean isComingIn) {
        this.pos = Integer.parseInt(pos);
        this.posName = posName;
        this.tag = tag;
        this.isComingIn = isComingIn;
    }

    Integer getPos() {
        return pos;
    }

    String getPosName() {
        return posName;
    }

    String getTag() {
        return tag;
    }

    Boolean getIsComingIn() {
        return isComingIn;
    }

    void setIsComingIn(Boolean action) {

        this.isComingIn = action;
    }
}
