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
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Workout;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PastWorkoutsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "PastWorkoutsFragment";

    private View view;
    private PastWorkoutViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<WorkoutPojo> allWorkouts;

    public PastWorkoutsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_past_workout, container, false);
        recyclerView = view.findViewById(R.id.workouts_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new GetAllWorkoutPojos().execute();
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
        adapter = new PastWorkoutViewAdapter(allWorkouts, getContext(), getFragmentManager());
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
        // TUpdate argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // make static to prevent memory leaks
    // https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private class GetAllWorkoutPojos extends AsyncTask<Void, Void, List<WorkoutPojo>> {

        @Override
        protected void onPostExecute(List<WorkoutPojo> workouts) {
            PastWorkoutsFragment.this.allWorkouts = new ArrayList<>(workouts);
            initRecycler();
        }

        @Override
        protected List<WorkoutPojo> doInBackground(Void... voids) {
            return Workout_DB.getInstance(getContext()).getWorkoutPojoDao().getAllWorkoutPojos();
        }

    }
}
