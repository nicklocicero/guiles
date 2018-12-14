package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

@Dao
public interface WorkoutPojoDao {

    @Query("SELECT * FROM workouts WHERE selected = 1")
    WorkoutPojo getWorkoutPojo();

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    Long insert(WorkoutPojo workoutPojo);
    @Query("SELECT * FROM workouts WHERE id =:id")
    WorkoutPojo select(long id);
}
