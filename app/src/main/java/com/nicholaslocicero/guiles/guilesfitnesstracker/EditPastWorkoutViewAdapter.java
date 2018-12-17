package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;

import java.util.List;

public class EditPastWorkoutViewAdapter extends RecyclerView.Adapter<EditPastWorkoutViewAdapter.WorkoutViewHolder> {

    private static final String TAG = "EditPastWorkoutViewAdapter";
    private Context context;
    private List<Exercise> exercises;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public EditPastWorkoutViewAdapter(List<Exercise> exercises, Context context,FragmentManager manager) {
        this.manager = manager;
        this.exercises = exercises;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_text_row_edit, parent, false);
        WorkoutViewHolder vh = new WorkoutViewHolder(v, new MyCustomEditTextListener(), new MyCustomEditTextListener(),
                new MyCustomEditTextListener(), new MyCustomEditTextListener());
        return vh;
    }



    // View lookup cache
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public EditText workoutNameEditText;
        public EditText setsEditText;
        public EditText repsEditText;
        public EditText weightEditText;
        public MyCustomEditTextListener workoutListener;
        public MyCustomEditTextListener setsListener;
        public MyCustomEditTextListener repsListener;
        public MyCustomEditTextListener weightListener;

        public WorkoutViewHolder(LinearLayout v, MyCustomEditTextListener workoutListener,
                                 MyCustomEditTextListener setsListener,
                                 MyCustomEditTextListener repsListener,
                                 MyCustomEditTextListener weightListener) {
            super(v);
            this.workoutListener = workoutListener;
            this.setsListener = setsListener;
            this.repsListener = repsListener;
            this.weightListener = weightListener;
            workoutNameEditText = v.findViewById(R.id.exercise_name_edit);
            workoutListener.setColumn("workout");
            workoutNameEditText.addTextChangedListener(workoutListener);
            setsEditText = v.findViewById(R.id.exercise_sets_edit);
            setsListener.setColumn("sets");
            setsEditText.addTextChangedListener(setsListener);
            repsEditText = v.findViewById(R.id.exercise_reps_edit);
            repsListener.setColumn("reps");
            repsEditText.addTextChangedListener(repsListener);
            weightEditText = v.findViewById(R.id.exercise_weight_edit);
            weightListener.setColumn("weightEditText");
            weightEditText.addTextChangedListener(weightListener);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final WorkoutViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.workoutListener.updatePosition(position);
        holder.setsListener.updatePosition(position);
        holder.repsListener.updatePosition(position);
        holder.weightListener.updatePosition(position);
        holder.workoutNameEditText.setText(exercises.get(position).getWorkoutName());
        if (exercises.get(position).getSets() != null) {
            holder.setsEditText.setText(exercises.get(position).getSets().toString());
        } else {
            holder.setsEditText.setText("");
        }
        if (exercises.get(position).getReps() != null) {
            holder.repsEditText.setText(exercises.get(position).getReps().toString());
        } else {
            holder.repsEditText.setText("");
        }
        if (exercises.get(position).getWeight() != null) {
            holder.weightEditText.setText(exercises.get(position).getWeight().toString());
        } else {
            holder.weightEditText.setText("");
        }
    }



    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private String column;

        public void updatePosition(int position) {
            this.position = position;
        }

        private void setColumn(String column) {
            this.column = column;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            switch (column) {
                case "workout":
                    if (exercises.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (exercises.get(position) != null) { // changed to != null for removing disappearing data bug
//                                exercises.get(position) = new Exercise();
//                                exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                        exercises.get(position).setWorkoutName(charSequence.toString());
                    }
                    break;
                case "sets":
                    if (exercises.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (exercises.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            exercises.get(position).setSets(Integer.parseInt(charSequence.toString()));
                        }
                    }
                    break;
                case "reps":
                    if (exercises.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (exercises.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            exercises.get(position).setReps(Integer.parseInt(charSequence.toString()));
                        }
                    }
                    break;
                case "weightEditText":
                    if (exercises.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (exercises.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            exercises.get(position).setWeight(Integer.parseInt(charSequence.toString()));
                        }
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}