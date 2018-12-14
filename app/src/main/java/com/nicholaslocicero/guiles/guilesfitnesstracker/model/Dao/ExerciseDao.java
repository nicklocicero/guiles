package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(Exercise... exercises);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(List<Exercise> exercises);

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutid")
    List<Exercise> getCurrentWorkout(Long workoutid);

}
