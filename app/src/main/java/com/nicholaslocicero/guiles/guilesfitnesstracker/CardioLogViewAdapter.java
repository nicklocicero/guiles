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

import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.CardioWorkout;
import com.nicholaslocicero.guiles.guilesfitnesstracker.model.Entities.Exercise;

import java.util.List;

public class CardioLogViewAdapter extends RecyclerView.Adapter<CardioLogViewAdapter.ViewHolder>{

    private static final String TAG = "CardioLogViewAdapter";
    private Context context;
    private List<CardioWorkout> cardioWorkouts;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public CardioLogViewAdapter(List<CardioWorkout> cardioWorkouts, Context context,FragmentManager manager) {
        this.manager = manager;
        this.cardioWorkouts = cardioWorkouts;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardio_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: started");
        holder.bind(cardioWorkouts.get(position));
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
        TextView time;
        TextView distance;
        TextView route;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.cardio_date);
            time = itemView.findViewById(R.id.cardio_duration);
            distance = itemView.findViewById(R.id.cardio_distance);
            route = itemView.findViewById(R.id.cardio_route);
            itemView.setOnClickListener(this);
        }

        public void bind(CardioWorkout cardioWorkout) {
            this.cardioWorkout = cardioWorkout;
            date.setText(cardioWorkout.getDate().toString());
            time.setText(cardioWorkout.getMinutes() == null ? "" : Float.toString(cardioWorkout.getMinutes()));
            distance.setText(cardioWorkout.getMiles() == null ? "" : Float.toString(cardioWorkout.getMinutes()));
            route.setText(cardioWorkout.getRoute() == null ? "" : cardioWorkout.getRoute());
        }

        @Override
        public void onClick(View v) {

        }
    }
}