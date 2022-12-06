package com.example.appmovie.db.db_actor;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appmovie.model.Actor;

@Database(entities = {Actor.class}, version = 1, exportSchema = false)
public abstract class ActorDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ActorManagement.db";
    private static ActorDatabase instance;

    public static synchronized ActorDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ActorDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }

    public abstract ActorDAO actorDAO();
}
