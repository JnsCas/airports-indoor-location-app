package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

public class Location {

    private Integer id;
    private String abreviature;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbreviature() {
        return abreviature;
    }

    public void setAbreviature(String abreviature) {
        this.abreviature = abreviature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + abreviature + ")";
    }
}
