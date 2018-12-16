package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.WorkoutPojo;

import org.w3c.dom.Text;

import java.util.List;
/**
 * The type Scout view adapter.
 */
public class PastWorkoutViewAdapter extends RecyclerView.Adapter<PastWorkoutViewAdapter.ViewHolder>{

    private static final String TAG = "PastWorkoutViewAdapter";
    private Context context;
    private List<WorkoutPojo> workouts;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public PastWorkoutViewAdapter(List<WorkoutPojo> workouts, Context context,FragmentManager manager) {
        this.manager = manager;
        this.workouts = workouts;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_workouts_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: started");
        holder.bind(workouts.get(position));
    }


    @Override
    public int getItemCount() {
        return workouts.size();
    }

    /**.
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        WorkoutPojo workoutPojo;
        TextView dateText;
        LinearLayout firstColumn;
        LinearLayout secondColumn;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.workout_date);
            firstColumn = itemView.findViewById(R.id.first_column_past_workouts);
            secondColumn = itemView.findViewById(R.id.second_column_past_workouts);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new Toast(context).makeText(context, "THIS", Toast.LENGTH_LONG).show();
        }

        public void bind(WorkoutPojo workoutPojo) {
            this.workoutPojo = workoutPojo;
            dateText.setText(workoutPojo.getWorkout().getDate().toString());
            int c = 0;
            for (int i = 0; i < workoutPojo.getExercises().size(); i++) {
                // add all exercise stats
                String workoutName = workoutPojo.getExercises().get(i).getWorkoutName();
                if (!workoutName.equals("")) {
                    if (c % 2 == 0) {
                        LinearLayout workout = (LinearLayout) LayoutInflater.from(context)
                                .inflate(R.layout.exercise_text_row, firstColumn, false);
                        workout.setPadding(2,1,1,2);
                        TextView name = workout.findViewById(R.id.exercise_name);
                        TextView reps = workout.findViewById(R.id.exercise_reps);
                        TextView sets = workout.findViewById(R.id.exercise_sets);
                        TextView weight = workout.findViewById(R.id.exercise_weight);
                        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        reps.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        sets.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        weight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        name.setText(workoutName);
                        sets.setText(workoutPojo.getExercises().get(i).getSets() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getSets()));
                        reps.setText(workoutPojo.getExercises().get(i).getReps() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getReps()));
                        weight.setText(workoutPojo.getExercises().get(i).getWeight() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getWeight()));
                        firstColumn.addView(workout);
                    } else {
                        LinearLayout workout = (LinearLayout) LayoutInflater.from(context)
                                .inflate(R.layout.exercise_text_row, secondColumn, false);
                        workout.setPadding(2,1,1,2);
                        TextView name = workout.findViewById(R.id.exercise_name);
                        TextView reps = workout.findViewById(R.id.exercise_reps);
                        TextView sets = workout.findViewById(R.id.exercise_sets);
                        TextView weight = workout.findViewById(R.id.exercise_weight);
                        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        reps.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        sets.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        weight.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
                        name.setText(workoutName);
                        sets.setText(workoutPojo.getExercises().get(i).getSets() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getSets()));
                        reps.setText(workoutPojo.getExercises().get(i).getReps() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getReps()));
                        weight.setText(workoutPojo.getExercises().get(i).getWeight() == null ? "" : Integer.toString(workoutPojo.getExercises().get(i).getWeight()));
                        secondColumn.addView(workout);
                    }
                    c++;
                }
            }
        }
    }
}