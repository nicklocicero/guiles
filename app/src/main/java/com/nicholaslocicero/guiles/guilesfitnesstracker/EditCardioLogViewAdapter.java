package com.nicholaslocicero.guiles.guilesfitnesstracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.CardioWorkout;

import java.util.Date;
import java.util.List;

public class EditCardioLogViewAdapter extends RecyclerView.Adapter<EditCardioLogViewAdapter.ViewHolder>{

    private static final String TAG = "EditCardioLogViewAdapte";
    private Context context;
    private List<CardioWorkout> cardioWorkouts;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public List<CardioWorkout> getCardioWorkouts() {
        return cardioWorkouts;
    }

    public EditCardioLogViewAdapter(List<CardioWorkout> cardioWorkouts, Context context, FragmentManager manager) {
        this.manager = manager;
        this.cardioWorkouts = cardioWorkouts;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_cardio_row, parent, false);
        return new ViewHolder(view, new MyCustomEditTextListener(), new MyCustomEditTextListener(), new MyCustomEditTextListener(), new MyCustomEditTextListener());
    }


    @Override
    public int getItemCount() {
        return cardioWorkouts.size();
    }

    /**.
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardioWorkout cardioWorkout;
        TextView date;
        EditText time;
        EditText distance;
        EditText route;
        public EditCardioLogViewAdapter.MyCustomEditTextListener dateListener;
        public EditCardioLogViewAdapter.MyCustomEditTextListener timeListener;
        public EditCardioLogViewAdapter.MyCustomEditTextListener distanceListener;
        public EditCardioLogViewAdapter.MyCustomEditTextListener routeListener;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView, EditCardioLogViewAdapter.MyCustomEditTextListener dateListener,
                          EditCardioLogViewAdapter.MyCustomEditTextListener timeListener,
                          EditCardioLogViewAdapter.MyCustomEditTextListener distanceListener,
                          EditCardioLogViewAdapter.MyCustomEditTextListener routeListener) {
            super(itemView);
            this.dateListener = dateListener;
            this.timeListener = timeListener;
            this.distanceListener = distanceListener;
            this.routeListener = routeListener;
            date = itemView.findViewById(R.id.edit_cardio_date);
            time = itemView.findViewById(R.id.edit_cardio_duration);
            timeListener.setColumn("time");
            time.addTextChangedListener(timeListener);
            distance = itemView.findViewById(R.id.edit_cardio_distance);
            distanceListener.setColumn("distance");
            distance.addTextChangedListener(distanceListener);
            route = itemView.findViewById(R.id.edit_cardio_route);
            routeListener.setColumn("route");
            route.addTextChangedListener(routeListener);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(final EditCardioLogViewAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.dateListener.updatePosition(position);
        holder.timeListener.updatePosition(position);
        holder.distanceListener.updatePosition(position);
        holder.routeListener.updatePosition(position);

        holder.date.setText(cardioWorkouts.get(position).getDate() == null ? "" : android.text.format.DateFormat.format("MM/dd/yy", cardioWorkouts.get(position).getDate()));
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.pick_date, null);
                    Spinner monthSpinner = (Spinner) dialogView.findViewById(R.id.month);
                    ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(context,
                            R.array.months, android.R.layout.simple_spinner_item);
                    monthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
                    monthSpinner.setAdapter(monthAdapter);
                    Spinner daySpinner = (Spinner) dialogView.findViewById(R.id.day);
                    ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(context,
                            R.array.day, android.R.layout.simple_spinner_item);
                    dayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
                    daySpinner.setAdapter(dayAdapter);
                    Spinner yearSpinner = (Spinner) dialogView.findViewById(R.id.year);
                    ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(context,
                            R.array.year, android.R.layout.simple_spinner_item);
                    yearAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
                    yearSpinner.setAdapter(yearAdapter);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context);
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            cardioWorkouts.get(position).setDate(new Date(year, month, dayOfMonth));
                            holder.date.setText(android.text.format.DateFormat.format(
                                    "MM/dd/yy", cardioWorkouts.get(position).getDate()));
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });
        holder.time.setText(cardioWorkouts.get(position).getMinutes() == null ? "" : Float.toString(cardioWorkouts.get(position).getMinutes()));
        holder.distance.setText(cardioWorkouts.get(position).getMiles() == null ? "" : Float.toString(cardioWorkouts.get(position).getMinutes()));
        holder.route.setText(cardioWorkouts.get(position).getRoute() == null ? "" : cardioWorkouts.get(position).getRoute());
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
                case "date":
                    break;
                case "time":
                    if (cardioWorkouts.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (cardioWorkouts.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            cardioWorkouts.get(position).setMinutes(Float.parseFloat(charSequence.toString()));
                        }
                    }
                    break;
                case "distance":
                    if (cardioWorkouts.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (cardioWorkouts.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            cardioWorkouts.get(position).setMiles(Float.parseFloat(charSequence.toString()));
                        }
                    }
                    break;
                case "route":
                    if (cardioWorkouts.get(position) == null && charSequence.length() == 0) {
                        break;
                    } else if (charSequence.length() != 0) {
                        if (cardioWorkouts.get(position) != null) {
//                                    exercises.get(position) = new Exercise();
//                                    exercises.get(position).setWorkoutId(RapidWorkoutApplication.getInstance().getWorkoutId());
                            cardioWorkouts.get(position).setRoute(charSequence.toString());
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