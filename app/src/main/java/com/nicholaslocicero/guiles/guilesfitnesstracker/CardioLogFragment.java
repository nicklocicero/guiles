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
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.CardioWorkout;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import java.util.List;

public class CardioLogFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "CardioLogFragment";

    private View view;
    private CardioLogViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<CardioWorkout> cardioWorkouts;
    private Button editButton;

    public CardioLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cardio_log, container, false);
        recyclerView = view.findViewById(R.id.exercises_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editButton = view.findViewById(R.id.edit_cardio_log_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWorkoutFragment workoutFragment = new EditWorkoutFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("id", workoutPojo.getWorkout().getId());
                workoutFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, workoutFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        new GetCardioWorkouts().execute();
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
        adapter = new CardioLogViewAdapter(cardioWorkouts, getContext(), getFragmentManager());
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
    private class GetCardioWorkouts extends AsyncTask<Void, Void, List<CardioWorkout>> {

        @Override
        protected void onPostExecute(List<CardioWorkout> cardioWorkouts) {
            CardioLogFragment.this.cardioWorkouts = cardioWorkouts;
            initRecycler();
        }

        @Override
        protected List<CardioWorkout> doInBackground(Void... voids) {
            return Workout_DB.getInstance(getContext()).getCardioWorkoutDao().getCardioWorkouts();
        }

    }
}
