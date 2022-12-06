package com.example.appmovie.db.db_actor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appmovie.model.Actor;

import java.util.List;

@Dao
public interface ActorDAO {
    @Insert
    void insert(Actor actor);

    @Query("SELECT * FROM ACTOR ORDER BY `FullName` ASC")
    List<Actor> getActor();

    @Update
    void update(Actor actor);

    @Delete
    void delete(Actor actor);

    @Query("SELECT `FullName` FROM  ACTOR ORDER BY `FullName` ASC")
    List<String> getActorName();

    @Query("SELECT * FROM ACTOR WHERE `FullName` LIKE '%' || :input || '%'")
    Actor searchActorByName(String input);

    @Query("SELECT * FROM ACTOR WHERE `FullName` LIKE '%' || :name || '%' ")
    List<Actor> searchActorByName2(String name);
}
