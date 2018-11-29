package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

public class BoardingGate {

    private Long id;
    private Integer number;
    private String beaconTag;

    public BoardingGate() {

    }

    public BoardingGate(Long id, Integer number, String beaconTag) {
        this.id = id;
        this.number = number;
        this.beaconTag = beaconTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBeaconTag() {
        return beaconTag;
    }

    public void setBeaconTag(String beaconTag) {
        this.beaconTag = beaconTag;
    }
}
