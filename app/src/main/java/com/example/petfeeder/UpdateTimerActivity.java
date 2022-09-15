package com.example.petfeeder;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
//Class to update and delete objects in the database
public class UpdateTimerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTimerName, editNote, editAmount;
    private TextView viewDate, viewTime, editID;
    private com.example.petfeeder.DBHandler dbHandler;
    private Button updateButton, deleteButton, btUpdateTime, btUpdateDate;

    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;

    String ID, timerName, date, time, amount, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        editID = findViewById(R.id.editID);
        editTimerName = findViewById(R.id.editTimerName);
        editAmount = findViewById(R.id.editAmount);
        editNote = findViewById(R.id.editNote);
        viewDate = findViewById(R.id.viewDate);
        viewTime = findViewById(R.id.viewTime);

        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        btUpdateTime = findViewById(R.id.btUpdateTime);
        btUpdateDate = findViewById(R.id.btUpdateDate);

        dbHandler = new com.example.petfeeder.DBHandler(UpdateTimerActivity.this);

        ID = getIntent().getStringExtra("ID");
        timerName = getIntent().getStringExtra("timer name");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        amount = getIntent().getStringExtra("amount");
        notes = getIntent().getStringExtra("notes");

        editID.setText(ID);
        editTimerName.setText(timerName);
        viewDate.setText(date);
        viewTime.setText(time);
        editAmount.setText(amount);
        editNote.setText(notes);

        btUpdateDate.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        });

        btUpdateTime.setOnClickListener(v -> buttonSelectTime());
//calls upon dbHandler class' updateTimer method
        updateButton.setOnClickListener(v -> {
            try {
                String tempTimerID = editID.getText().toString();
                String timerName = editTimerName.getText().toString();
                String date = viewDate.getText().toString();
                String time = viewTime.getText().toString();
                String  amount = editAmount.getText().toString();

                //Ensures that required fields are completed
                if (tempTimerID.isEmpty() || timerName.equals("") || date.equals("") || time.equals("") || amount.equals("")) {
                    Toast.makeText(UpdateTimerActivity.this, "Timer Not Updated\nA Field is Missing",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    dbHandler.addNewTimer(ID, timerName, date, time, amount, notes);
                }
                dbHandler.updateTimer(ID, tempTimerID,
                        editTimerName.getText().toString(),
                        viewDate.getText().toString(),
                        viewTime.getText().toString(),
                        editAmount.getText().toString(),
                        editNote.getText().toString());

                Toast.makeText(UpdateTimerActivity.this, "Timer Record Updated",
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdateTimerActivity.this, com.example.petfeeder.ScheduleFeed.class);
                startActivity(i);
            }
            catch (Exception e){
                Toast.makeText(UpdateTimerActivity.this, "Something went wrong; " +
                                "ensure you are not adding a duplicate record and try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
//delete button calls upon dbHandler class' deleteTimer method
        deleteButton.setOnClickListener(v -> {
            try {
                dbHandler.deleteTimer(ID);
                Toast.makeText(UpdateTimerActivity.this, "Record Deleted",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateTimerActivity.this, com.example.petfeeder.ScheduleFeed.class);
                startActivity(i);
            }
            catch (Exception e){
                Toast.makeText(UpdateTimerActivity.this, "Something went wrong; try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
//select TimePickerDialog widget method
    private void buttonSelectTime()  {

        final boolean is24HView = false;

        if(this.lastSelectedHour == -1)  {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinute = c.get(Calendar.MINUTE);
        }
        // Time Set Listener.
        @SuppressLint("DefaultLocale") TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {

            boolean isPM = (hourOfDay >= 12);
            viewTime.setText
                    (String.format
                            ("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0)
                                    ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
        };

        // Create TimePickerDialog:
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
        // Show
        timePickerDialog.show();
    }
    // Sets date from DatePicker widget
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        viewDate.setText(selectedDate);
    }
}
