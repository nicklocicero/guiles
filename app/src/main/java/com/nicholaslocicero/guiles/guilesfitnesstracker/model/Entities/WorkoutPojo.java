package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Relation;
import java.util.List;

public class WorkoutPojo {

    @Embedded
    private Workout workout;
    @Relation(parentColumn = "id", entityColumn = "workoutId", entity = Exercise.class)
    private List<Exercise> exercises;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
