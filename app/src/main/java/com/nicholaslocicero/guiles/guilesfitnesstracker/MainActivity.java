package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB.Workout_DB;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.ExerciseDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.WorkoutDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Dao.WorkoutPojoDao;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB.Workout_DB.getInstance;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WorkoutFragment.OnFragmentInteractionListener,
        EditWorkoutFragment.OnFragmentInteractionListener,
        PastWorkoutsFragment.OnFragmentInteractionListener,
        EditPastWorkoutFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutFragment()).commit();
        new ForcePopulateTask().execute(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.submit_workout) {
            WorkoutFragment workoutFragment = (WorkoutFragment) getSupportFragmentManager().findFragmentByTag("WorkoutFragment");
            if (workoutFragment != null && workoutFragment.isVisible()) {
                new SubmitWorkout().execute(workoutFragment.getWorkout());
                return true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // TODO cardio, too
        // TODO export CSV
        // TODO Play store
        // TODO Google cloud
        // TODO Add fitbit
        // TODO Google Cloud Professional Cloud Developer
        int id = item.getItemId();

        if (id == R.id.current_workout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutFragment()).commit();
        } else if (id == R.id.past_workouts) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PastWorkoutsFragment()).commit();
        } else if (id == R.id.activity_log) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(Fragment fragment, boolean useStack, String variant) {
        FragmentManager manager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName() + ((variant != null) ? variant : "");
        if (manager.findFragmentByTag(tag) != null) {
            manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment, tag);
        if (useStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private static class ForcePopulateTask extends AsyncTask<Context, Void, Void> {


        @Override
        protected Void doInBackground(Context... contexts) {
            getInstance(contexts[0]).getWorkoutPojoDao().select(0);
            return null;
        }
    }

    private class SubmitWorkout extends AsyncTask<List<Exercise>, Void, Void> {
        @Override
        protected Void doInBackground(List<Exercise>... lists) {
            getInstance(getApplicationContext()).getExerciseDao().update(lists[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            new CreateNewWorkout().execute();
        }
    }

    private class CreateNewWorkout extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Workout_DB db = getInstance(getApplicationContext());

            WorkoutDao workoutDao = db.getWorkoutDao();
            ExerciseDao exerciseDao = db.getExerciseDao();
            WorkoutPojoDao workoutPojoDao = db.getWorkoutPojoDao();

            workoutPojoDao.unselectAll(new Date());

            Workout workout = new Workout();
            Long id = workoutDao.insert(workout);

            List<Exercise> exercises = new ArrayList<>();

            for (int i =  0; i < 20; i++) {
                Exercise exercise = new Exercise();
                exercise.setWorkoutId(id);
                exercises.add(exercise);
            }

            exerciseDao.insert(exercises);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutFragment()).commit();
            new ForcePopulateTask().execute(MainActivity.this);
        }
    }
}
