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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.DB.Workout_DB;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import java.util.List;

public class EditPastWorkoutFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "EditPastWorkoutFragment";

    private View view;
    private EditPastWorkoutViewAdapter adapter;
    private RecyclerView recyclerView;
    private WorkoutPojo workoutPojo;
    private Button editButton;
    private Bundle bundle;
    private Long workoutId;

    public EditPastWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
            workoutId = bundle.getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.edit_past_fragment_workout, container, false);
        recyclerView = view.findViewById(R.id.exercises_recycler_view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editButton = view.findViewById(R.id.save_past_workout_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                new SaveCurrentWorkout().execute(adapter.getExercises());
            }
        });
        new GetWorkoutPojo().execute(workoutId);
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
        adapter = new EditPastWorkoutViewAdapter(workoutPojo.getExercises(), getContext(), getFragmentManager());
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
    private class GetWorkoutPojo extends AsyncTask<Long, Void, WorkoutPojo> {

        @Override
        protected void onPostExecute(WorkoutPojo workoutPojo) {
            EditPastWorkoutFragment.this.workoutPojo = workoutPojo;
            initRecycler();
        }

        @Override
        protected WorkoutPojo doInBackground(Long... longs) {
            return Workout_DB.getInstance(getContext()).getWorkoutPojoDao().getSelectedWorkoutPojo(longs[0]);
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
            getActivity().onBackPressed();;
        }
    }
}

