package com.example.appmovie.db.db_movie_favorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appmovie.model.MovieFavorite;

import java.util.List;

@Dao
public interface MovieFavoriteDAO {
    @Insert
    void insert(MovieFavorite movieFavorite);

    @Query("SELECT * FROM MOVIE_FAVORITE ORDER BY `Movie Name` ASC")
    List<MovieFavorite> getMoviesFavorite();

    @Delete
    void delete(MovieFavorite movieFavorite);

    @Query("SELECT * FROM MOVIE_FAVORITE WHERE `Movie Name` LIKE :input")
    MovieFavorite filterMovieFavoriteByName(String input);

    @Query("SELECT * FROM MOVIE_FAVORITE WHERE `Movie Name` LIKE :input")
    List<MovieFavorite> filterListMovieFavoriteByName(String input);
}
