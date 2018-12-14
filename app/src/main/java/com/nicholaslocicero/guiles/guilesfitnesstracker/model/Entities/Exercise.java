package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Workout;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Exercise entity.
 */
@Entity(
        tableName = "exercises",
        foreignKeys = @ForeignKey(
                entity = Workout.class,
                parentColumns = "id",
                childColumns = "workoutId",
                onDelete = CASCADE),
        indices = {
                @Index(value = {"id"}, unique = true)
        }
)
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String workoutName = "";
    private Long workoutId;
    private Integer sets;
    private Integer reps;
    private Integer weight;

    public String getExerciseName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
