package com.example.appmovie.db.db_movie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insert(Movie movie);

    @Query("SELECT * FROM MOVIE ORDER BY `Movie Name` ASC")
    List<Movie> getMovie();

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM MOVIE WHERE `Directors` LIKE '%' || :input || '%' ORDER BY `Movie Name` ASC")
    List<Movie> searchMovieByDirectorName(String input);

    @Query("SELECT * FROM MOVIE WHERE `Actors` LIKE '%' || :input || '%' ORDER BY `Movie Name` ASC")
    List<Movie> searchMovieByActorName(String input);

    @Query("SELECT * FROM MOVIE WHERE `Category` LIKE :categoryInput ORDER BY `Movie Name` ASC")
    List<Movie> filterMovieByCategory(String categoryInput);

    @Query("SELECT * FROM MOVIE WHERE `Movie Name` LIKE :nameInput")
    Movie getMovieByName(String nameInput);

    @Query("SELECT * FROM MOVIE WHERE `Category` Like :category AND `Movie Name` LIKE :name")
    List<Movie> filterMovieByCategoryAndName(String category, String name);

    @Query("SELECT * FROM MOVIE WHERE `Movie Name` LIKE '%' || :input || '%'")
    List<Movie> filterMovieByName(String input);
}
