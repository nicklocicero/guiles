package com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "cardio_log"
)
public class CardioWorkout {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    Date date;
    String route = "";
    Float minutes;
    Float miles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Float getMiles() {
        return miles;
    }

    public void setMiles(Float miles) {
        this.miles = miles;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
