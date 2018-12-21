package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.CardioWorkout;

import java.util.List;

@Dao
public interface CardioWorkoutDao {

    @Query("SELECT * FROM cardio_log")
    List<CardioWorkout> getCardioWorkouts();

    @Insert
    Long insert(CardioWorkout cardioWorkout);

    @Insert
    List<Long> insert(List<CardioWorkout> cardioWorkouts);

    @Update
    int updateAll(List<CardioWorkout> cardioWorkouts);

}
