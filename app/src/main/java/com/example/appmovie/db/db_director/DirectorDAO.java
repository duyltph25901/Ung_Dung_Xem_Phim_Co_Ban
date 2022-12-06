package com.example.appmovie.db.db_director;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appmovie.model.Actor;
import com.example.appmovie.model.Director;

import java.util.List;

@Dao
public interface DirectorDAO {
    @Insert
    void insert(Director director);

    @Query("SELECT * FROM DIRECTOR ORDER BY `FullName` ASC")
    List<Director> getDirector();

    @Update
    void update(Director director);

    @Delete
    void delete(Director director);

    @Query("SELECT `FullName` FROM DIRECTOR ORDER BY `FullName` ASC")
    List<String> getDirectorsName();

    @Query("SELECT * FROM DIRECTOR WHERE `FullName` LIKE :input")
    Director searchDirectorByName(String input);

    @Query("SELECT * FROM DIRECTOR WHERE `FullName` LIKE '%' || :name || '%' ")
    List<Director> searchDirectorByName2(String name);
}
