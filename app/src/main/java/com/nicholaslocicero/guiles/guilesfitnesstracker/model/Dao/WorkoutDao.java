package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Workout;

import java.util.List;

@Dao
public abstract interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Workout workout);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(Workout... workouts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<Workout> workouts);

    @Query("SELECT * FROM workouts")
    List<Workout> getAllWorkouts();
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    int update(Workout workout);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    int update(Workout... workouts);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    int update(List<Workout> workouts);

    @Query("SELECT * FROM workouts WHERE selected = 1")
    Workout selectLastWorkout();
}
