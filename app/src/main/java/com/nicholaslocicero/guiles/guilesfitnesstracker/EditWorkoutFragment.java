package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB.Workout_DB;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import java.util.List;

public class EditWorkoutFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "ScoutFragment";

    private View view;
    private EditWorkoutViewAdapter adapter;
    private RecyclerView recyclerView;
    private WorkoutPojo workoutPojo;
    private Button editButton;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_workout, container, false);
        recyclerView = view.findViewById(R.id.exercises_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editButton = view.findViewById(R.id.edit_workout_button);
        editButton.setText("Save");
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveCurrentWorkout().execute(adapter.getExercises());
            }
        });
        new GetWorkoutPojo().execute();
    }

    // Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecycler() {
        adapter = new EditWorkoutViewAdapter(workoutPojo.getExercises(), getContext(), getFragmentManager());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void switchFragment(Fragment fragment, boolean useStack, String variant) {
        FragmentManager manager = getFragmentManager();
        String tag = fragment.getClass().getSimpleName() + ((variant != null) ? variant : "");
        if (manager.findFragmentByTag(tag) != null) {
            manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        if (useStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
        //  ((MainActivity) getActivity()).updateData();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        new SaveCurrentWorkout().execute(adapter.getExercises());
//    }

    //    @Override
//    public void onPause() {
//        super.onPause();
//    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // make static to prevent memory leaks
    // https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private class GetWorkoutPojo extends AsyncTask<Void, Void, WorkoutPojo> {

        @Override
        protected void onPostExecute(WorkoutPojo workoutPojo) {
            EditWorkoutFragment.this.workoutPojo = workoutPojo;
            initRecycler();
        }

        @Override
        protected WorkoutPojo doInBackground(Void... voids) {
            return Workout_DB.getInstance(getContext()).getWorkoutPojoDao().getWorkoutPojo();
        }

    }

    private class SaveCurrentWorkout extends AsyncTask<List<Exercise>, Void, Void> {

        @Override
        protected Void doInBackground(List<Exercise>... exercises) {
            Workout_DB.getInstance(getContext()).getExerciseDao().update(exercises[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            switchFragment(new WorkoutFragment(), true, "");
        }
    }
}

