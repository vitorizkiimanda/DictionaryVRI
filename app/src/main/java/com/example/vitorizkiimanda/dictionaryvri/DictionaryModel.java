package com.example.vitorizkiimanda.dictionaryvri;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable {
    private int id;
    private String original_word;
    private String content_word;

    public DictionaryModel(String s, String s1) {
        this.original_word = s;
        this.content_word = s1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_word() {
        return original_word;
    }

    public void setOriginal_word(String original_word) {
        this.original_word = original_word;
    }

    public String getContent_word() {
        return content_word;
    }

    public void setContent_word(String content_word) {
        this.content_word = content_word;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.original_word);
        dest.writeString(this.content_word);
    }

    public DictionaryModel() {
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.original_word = in.readString();
        this.content_word = in.readString();
    }

    public static final Parcelable.Creator<DictionaryModel> CREATOR = new Parcelable.Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
