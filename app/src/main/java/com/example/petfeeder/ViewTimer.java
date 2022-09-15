package com.example.petfeeder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//ViewTimer class that uses the activity_view_timer layout to display items from the TimerViewer arraylist
public class ViewTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timer);

        ArrayList<TimerModal> timerModelArrayList;
        DBHandler dbHandler = new DBHandler(ViewTimer.this);

        timerModelArrayList = dbHandler.viewTimer();

        TimerViewer timerViewer = new TimerViewer(timerModelArrayList, ViewTimer.this);
        RecyclerView timerRV = findViewById(R.id.TimerRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (ViewTimer.this, RecyclerView.VERTICAL, false);
        timerRV.setLayoutManager(linearLayoutManager);

        timerRV.setAdapter(timerViewer);
    }
}
