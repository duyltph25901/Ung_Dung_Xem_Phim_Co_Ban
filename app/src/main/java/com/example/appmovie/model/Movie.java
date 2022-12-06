package com.example.appmovie.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

@Entity(tableName = "MOVIE")
public class Movie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long id;
    @ColumnInfo(name = "Poster")
    private byte[] poster;
    @ColumnInfo(name = "Link Trailer")
    private String linkTrailer;
    @ColumnInfo(name = "Link Movie")
    private String linkFilm;
    @ColumnInfo(name = "Movie Name")
    private String movieName;
    @ColumnInfo(name = "Directors")
    private String directors;
    @ColumnInfo(name = "Actors")
    private String actors;
    @ColumnInfo(name = "Premiere Schedule")
    private String premiereSchedule;
    @ColumnInfo(name = "Category")
    private String category;
    @ColumnInfo(name = "Summary")
    private String summary;
    @ColumnInfo(name = "Time")
    private int time;
    @ColumnInfo(name = "Limit Age")
    private int limitAge;
    @ColumnInfo(name = "Point")
    private double point;

    public Movie(byte[] poster, String linkTrailer, String linkFilm,
                 String movieName, String directors, String actors,
                 String premiereSchedule, String category, String summary,
                 int time, int limitAge, double point) {
        this.poster = poster;
        this.linkTrailer = linkTrailer;
        this.linkFilm = linkFilm;
        this.movieName = movieName;
        this.directors = directors;
        this.actors = actors;
        this.premiereSchedule = premiereSchedule;
        this.category = category;
        this.summary = summary;
        this.time = time;
        this.limitAge = limitAge;
        this.point = point;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getLinkFilm() {
        return linkFilm;
    }

    public void setLinkFilm(String linkFilm) {
        this.linkFilm = linkFilm;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirectors() {
        return directors.toString();
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors.toString();
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPremiereSchedule() {
        return premiereSchedule;
    }

    public void setPremiereSchedule(String premiereSchedule) {
        this.premiereSchedule = premiereSchedule;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLimitAge() {
        return limitAge;
    }

    public void setLimitAge(int limitAge) {
        this.limitAge = limitAge;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public static byte[] convertByteToBitmap(byte[] input) {
        Bitmap bitmap = BitmapFactory.decodeFile("/path/images/image.jpg");
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        byte[] bitPoster = input;
        bitPoster = blob.toByteArray();

        return bitPoster;
    }
}
