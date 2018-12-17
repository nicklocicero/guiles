package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.CardioWorkout;

import java.util.List;

@Dao
public interface CardioWorkoutDao {

    @Query("SELECT * FROM cardio_log")
    List<CardioWorkout> getCardioWorkouts();

}
