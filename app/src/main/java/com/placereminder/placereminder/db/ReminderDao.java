package com.placereminder.placereminder.db;

import com.placereminder.placereminder.model.Reminderdb;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM Reminderdb")
    List<Reminderdb> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTradeHistory(Reminderdb reminderdb);
}
