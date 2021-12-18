package com.adib.mynote.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);
}
