package it.thetarangers.thetacarte;


import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    public String eventName;
    public String eventDescription;
    public String eventLocation;
    public int imageId;

    public Event(String eventName, String eventDescription, int imageId, String eventLocation) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.imageId = imageId;
        this.eventLocation = eventLocation;
    }

    protected Event(Parcel in) {
        eventName = in.readString();
        eventDescription = in.readString();
        eventLocation = in.readString();
        imageId = in.readInt();
    }


    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventDescription);
        dest.writeString(eventLocation);
        dest.writeInt(imageId);
    }
}