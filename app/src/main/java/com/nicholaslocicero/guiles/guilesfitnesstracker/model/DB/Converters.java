package com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date dateFromTimestamp(Long timestamp) {
        return (timestamp != null) ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long timestampFromDate(Date date) {
        return (date != null) ? date.getTime() : null;
    }

}
