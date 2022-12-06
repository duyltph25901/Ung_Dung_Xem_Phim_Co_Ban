package com.example.appmovie.db.db_movie_favorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appmovie.model.MovieFavorite;

@Database(entities = {MovieFavorite.class}, version = 1, exportSchema = false)
public abstract class MovieFavoriteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "MovieFavoriteManagement.db";
    private static MovieFavoriteDatabase instance;

    public static synchronized MovieFavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieFavoriteDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract MovieFavoriteDAO movieFavoriteDAO();
}
