package it.ferrarally.provacarte;


import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    public String eventName;
    public String eventDescription;
    public int imageId;

    public Event(String eventName, String eventDescription, int imageId){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.imageId = imageId;
    }

    protected Event(Parcel in) {
        eventName = in.readString();
        eventDescription = in.readString();
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
        dest.writeInt(imageId);
    }
}