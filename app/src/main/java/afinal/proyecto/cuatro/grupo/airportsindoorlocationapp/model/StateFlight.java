package afinal.proyecto.cuatro.grupo.airportsindoorlocationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StateFlight implements Parcelable {

    private Integer id;
    private String description;

    public StateFlight() {

    }

    public StateFlight(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    protected StateFlight(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        description = in.readString();
    }

    public static final Creator<StateFlight> CREATOR = new Creator<StateFlight>() {
        @Override
        public StateFlight createFromParcel(Parcel in) {
            return new StateFlight(in);
        }

        @Override
        public StateFlight[] newArray(int size) {
            return new StateFlight[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        parcel.writeString(description);
    }
}
