package com.adib.mynote.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    Single<List<Note>> getAll();

    @Insert
    Completable insert(Note note);

    @Delete
    Completable delete(Note note);
}
