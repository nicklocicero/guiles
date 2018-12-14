package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "workouts"
)
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Date date = new Date();
    private boolean selected = true;
    private boolean hasBeenSubmitted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setHasBeenSubmitted(boolean bool) {
        hasBeenSubmitted = bool;
    }

    public boolean isHasBeenSubmitted() {
        return hasBeenSubmitted;
    }
}
