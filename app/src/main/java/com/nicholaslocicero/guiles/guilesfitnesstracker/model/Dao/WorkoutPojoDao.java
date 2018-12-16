package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import java.util.Date;
import java.util.List;

@Dao
public interface WorkoutPojoDao {

    @Query("SELECT * FROM workouts WHERE selected = 1")
    WorkoutPojo getWorkoutPojo();

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    Long insert(WorkoutPojo workoutPojo);
    @Query("SELECT * FROM workouts WHERE id =:id")
    WorkoutPojo select(long id);

    @Query("UPDATE workouts SET selected = 0, hasBeenSubmitted = 1, date = :date WHERE selected = 1")
    void unselectAll(Date date);

    @Query("SELECT * FROM workouts")
    List<WorkoutPojo> getAllWorkoutPojos();
}
