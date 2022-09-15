package com.example.petfeeder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//TimerViewer Class that allows for the SQlite database to be viewed as a RecyclerView
public class TimerViewer extends RecyclerView.Adapter<TimerViewer.ViewHolder> {

    private final ArrayList<TimerModal> timerModelArrayList;
    private final Context context;

    public TimerViewer(ArrayList<TimerModal> timerModelArrayList, Context context) {
        this.timerModelArrayList = timerModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_viewer, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TimerModal modal = timerModelArrayList.get(position);
        holder.IDTextView.setText("Timer Number: " + modal.getID());
        holder.timerNameTextView.setText("Timer Name: " + modal.getTimerName());
        holder.dateTextView.setText("Date: " + modal.getDate());
        holder.timeTextView.setText("Time: " + modal.getTime());
        holder.amountTextView.setText("Amount: " + modal.getAmount());
        holder.noteTextView.setText("Notes: " + modal.getNotes());


        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, UpdateTimerActivity.class);

            i.putExtra("ID", modal.getID());
            i.putExtra("timer name", modal.getTimerName());
            i.putExtra("date", modal.getDate());
            i.putExtra("time", modal.getTime());
            i.putExtra("amount", modal.getAmount());
            i.putExtra("notes", modal.getNotes());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return timerModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView IDTextView, timerNameTextView,  dateTextView, timeTextView, amountTextView, noteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            IDTextView = itemView.findViewById(R.id.ID);
            timerNameTextView = itemView.findViewById(R.id.timerName);
            dateTextView = itemView.findViewById(R.id.date);
            timeTextView = itemView.findViewById(R.id.time);
            amountTextView = itemView.findViewById(R.id.amount);
            noteTextView = itemView.findViewById(R.id.notes);
        }
    }
}
