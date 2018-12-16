package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;

import java.util.List;

/**
 * The type Scout view adapter.
 */
public class WorkoutViewAdapter extends RecyclerView.Adapter<WorkoutViewAdapter.ViewHolder>{

    private static final String TAG = "WorkoutViewAdapter";
    private Context context;
    private List<Exercise> exercises;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public WorkoutViewAdapter(List<Exercise> exercises, Context context,FragmentManager manager) {
        this.manager = manager;
        this.exercises = exercises;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_text_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: started");
        holder.bind(exercises.get(position));
    }


    @Override
    public int getItemCount() {
        return exercises.size();
    }

    /**.
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Exercise exercise;
        TextView workoutName;
        TextView sets;
        TextView reps;
        TextView weight;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.exercise_name);
            sets = itemView.findViewById(R.id.exercise_sets);
            reps = itemView.findViewById(R.id.exercise_reps);
            weight = itemView.findViewById(R.id.exercise_weight);
            itemView.setOnClickListener(this);
        }

        public void bind(Exercise exercise) {
            this.exercise = exercise;
            workoutName.setText(exercise.getExerciseName());
            sets.setText(exercise.getSets() == null ? "" : Integer.toString(exercise.getSets()));
            reps.setText(exercise.getReps() == null ? "" : Integer.toString(exercise.getReps()));
            weight.setText(exercise.getWeight() == null ? "" : Integer.toString(exercise.getWeight()));
        }

        @Override
        public void onClick(View v) {

        }
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}