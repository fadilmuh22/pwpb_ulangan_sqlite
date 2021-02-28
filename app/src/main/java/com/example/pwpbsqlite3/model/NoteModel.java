package com.example.pwpbsqlite3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteModel implements Parcelable {
    public int id;
    public String date, title, body;

    public NoteModel() {}

    public NoteModel(Parcel in ) {
        readFromParcel( in );
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public NoteModel createFromParcel(Parcel in ) {
            return new NoteModel( in );
        }

        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(body);
    }

    private void readFromParcel(Parcel in ) {

        id = in .readInt();
        date = in .readString();
        title = in .readString();
        body = in .readString();
    }
}
