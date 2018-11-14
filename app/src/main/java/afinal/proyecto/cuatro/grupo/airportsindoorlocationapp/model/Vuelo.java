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

    public Vuelo() {

    }

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

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(number);
        if (boardingGate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(boardingGate);
        }
        if (boardingDateTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeLong(boardingDateTime.getTime());
        }
        parcel.writeParcelable(stateFlight, i);
        parcel.writeParcelable(destination, i);
    }

    protected Vuelo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        number = in.readString();
        if (in.readByte() == 0) {
            boardingGate = null;
        } else {
            boardingGate = in.readInt();
        }
        boardingDateTime = new Date(in.readLong());
        stateFlight = in.readParcelable(StateFlight.class.getClassLoader());
        destination = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Vuelo> CREATOR = new Creator<Vuelo>() {
        @Override
        public Vuelo createFromParcel(Parcel in) {
            return new Vuelo(in);
        }

        @Override
        public Vuelo[] newArray(int size) {
            return new Vuelo[size];
        }
    };
    */
}
