package com.vikvita.javaprojext;

import android.os.Parcel;
import android.os.Parcelable;

/**class which is used to contain info about each ready phrase */
public class Phrases implements Parcelable {
   private String phrase;



    public Phrases(String phrase) {
        setPhrase(phrase);


    }

    protected Phrases(Parcel in) {
        phrase = in.readString();
    }

    public static final Creator<Phrases> CREATOR = new Creator<Phrases>() {
        @Override
        public Phrases createFromParcel(Parcel in) {
            return new Phrases(in);
        }

        @Override
        public Phrases[] newArray(int size) {
            return new Phrases[size];
        }
    };

    public String getPhrase() {
        return phrase;
    }
    public void setPhrase(String phrase){
        this.phrase = phrase;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phrase);
    }
}
