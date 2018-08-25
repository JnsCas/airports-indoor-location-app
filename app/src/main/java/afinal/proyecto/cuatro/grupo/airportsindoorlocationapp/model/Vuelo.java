package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import java.util.Date;

/**
 * Created by JnsCas on 3/5/18.
 */

public class Vuelo {


    private Long id;
    private String number;
    private Date boardingDateTime;
    private Integer boardingGate;
    private StateFlight stateFlight;
    private Location destination;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getBoardingDateTime() {
        return boardingDateTime;
    }

    public void setBoardingDateTime(Date boardingDateTime) {
        this.boardingDateTime = boardingDateTime;
    }

    public Integer getBoardingGate() {
        return boardingGate;
    }

    public void setBoardingGate(Integer boardingGate) {
        this.boardingGate = boardingGate;
    }

    public StateFlight getStateFlight() {
        return stateFlight;
    }

    public void setStateFlight(StateFlight stateFlight) {
        this.stateFlight = stateFlight;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Vuelo " + number;
    }
}
