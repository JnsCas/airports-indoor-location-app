package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private Integer id;
    private String abreviature;
    private String name;

    public Location() {

    }

    public Location(Integer id, String abreviature, String name) {
        this.id = id;
        this.abreviature = abreviature;
        this.name = name;
    }

    protected Location(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        abreviature = in.readString();
        name = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

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
            parcel.writeInt(id);
        }
        parcel.writeString(abreviature);
        parcel.writeString(name);
    }
}
