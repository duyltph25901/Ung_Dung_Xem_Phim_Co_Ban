package com.example.appmovie.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ACTOR")
public class Actor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;
    @ColumnInfo(name = "Image")
    private byte[] img;
    @ColumnInfo(name = "FullName")
    private String fullName;
    @ColumnInfo(name = "Date Of Birth")
    private String DOB;
    @ColumnInfo(name = "Countries")
    private String countries;
    @ColumnInfo(name = "Gender")
    private String gender;
    @ColumnInfo(name = "Story")
    private String story;

    public Actor(byte[] img, String fullName, String DOB, String countries, String gender, String story) {
        this.img = img;
        this.fullName = fullName;
        this.DOB = DOB;
        this.countries = countries;
        this.gender = gender;
        this.story = story;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getFullName() {
        return fullName.toString();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
