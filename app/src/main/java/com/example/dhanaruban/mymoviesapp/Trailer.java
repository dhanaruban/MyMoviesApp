package com.example.dhanaruban.mymoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thenu on 31-01-2018.
 */

public class Trailer implements Parcelable {
    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public Trailer(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getiso_639_1() {
        return iso_639_1;
    }

    public void setiso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }
    public String getiso_3166_1() {
        return iso_3166_1;
    }

    public void setiso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(iso_639_1);
        parcel.writeString(iso_3166_1);
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(name);
        parcel.writeInt(size);
        parcel.writeString(type);

    }
    private Trailer(Parcel in){
        id =in.readString();
        iso_639_1 =in.readString();
        iso_3166_1=in.readString();
        key=in.readString();
        site=in.readString();
        name=in.readString();
        size=in.readInt();
        type=in.readString();
    }



    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };



}
