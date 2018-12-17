package com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.CardioWorkoutDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.ExerciseDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.WorkoutDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.WorkoutPojoDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Workout;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Exercise.class, Workout.class},
        version = 1,
        exportSchema = true)
@TypeConverters({Converters.class})
public abstract class Workout_DB extends RoomDatabase {

    public static final String DATABASE_NAME = "workout_db";

    private static Workout_DB instance = null;

    public synchronized static Workout_DB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Workout_DB.class, DATABASE_NAME)
                    .addCallback(new Callback(context.getApplicationContext()))
                    .build();
        }
        return instance;
    }

    public abstract ExerciseDao getExerciseDao();
    public abstract WorkoutDao getWorkoutDao();
    public abstract WorkoutPojoDao getWorkoutPojoDao();
    public abstract CardioWorkoutDao getCardioWorkoutDao();

    public static void forgetInstance(Context context) {
        instance = null;
    }

    private static class Callback extends RoomDatabase.Callback {

        private Context context;

        private Callback(Context context) {
            this.context = context;
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PrepopulateTask(context).execute(context); // Call a task to pre-populate database.
        }
    }

    private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

        private Context context;

        public PrepopulateTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Context... contexts) {

            Workout_DB db = getInstance(contexts[0]);

            WorkoutDao workoutDao = db.getWorkoutDao();
            ExerciseDao exerciseDao = db.getExerciseDao();

            Workout workout = new Workout();
            Long id = workoutDao.insert(workout);

            List<Exercise> exercises = new ArrayList<>();

            for (int i =  0; i < 20; i++) {
                Exercise exercise = new Exercise();
                exercise.setWorkoutId(id);
                exercises.add(exercise);
            }

            exerciseDao.insert(exercises);

            forgetInstance(contexts[0]);
            return null;

        }
    }

}

