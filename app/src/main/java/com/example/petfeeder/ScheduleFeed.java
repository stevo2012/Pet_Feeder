package com.example.petfeeder;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class ScheduleFeed extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    private int i = 1;

    private String ID = String.valueOf(i);
    private String date = null;
    private String timerName = null;
    private String time = null;
    private String amount = null;
    private String notes = "";

    private TextView enterDateViewText, timeTextView;
    private EditText enterTimerNameEditText, noteEditText, amountEditText;
    private Button buttonTime, btPickDate, menuButton, addButton, viewButton;
    private ImageButton sendFeederButton;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_schedule);

        buttonTime = findViewById(R.id.btPickTime);
        btPickDate = findViewById(R.id.btpickDate);
        menuButton = findViewById(R.id.mainMenuButton);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        sendFeederButton = findViewById(R.id.sendFeederButton);

        enterTimerNameEditText = findViewById(R.id.enterTimerNameEditText);
        enterDateViewText =  findViewById(R.id.enterDateViewText);
        timeTextView = findViewById(R.id.timeTextView);
        amountEditText = findViewById(R.id.amountEditText);
        noteEditText = findViewById(R.id.notesEditText);

        dbHandler = new DBHandler(ScheduleFeed.this);

        buttonTime.setOnClickListener(v -> buttonSelectTime());

        btPickDate.setOnClickListener(v -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        });

        sendFeederButton.setOnClickListener(v -> {
            if (!dbHandler.checkID(ID)){
                Toast.makeText(ScheduleFeed.this, "No Schedule Found\nPlease Add a Timer",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ScheduleFeed.this, "Schedule Sent to Feeder!!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        menuButton.setOnClickListener(v -> {
            openNewActivity();
            finish();
        });

        addButton.setOnClickListener(v -> {
            try{
                timerName = enterTimerNameEditText.getText().toString();
                date = enterDateViewText.getText().toString();
                time = timeTextView.getText().toString();
                amount = amountEditText.getText().toString();
                notes = noteEditText.getText().toString();

                //Ensures that required fields are completed
                if (timerName.equals("") || date.equals("") || time.equals("") || amount.equals("")){
                    Toast.makeText(ScheduleFeed.this, "Timer Not Added\nA Field is Missing",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (ID.isEmpty()) {
                    ID = String.valueOf(i);
                    Toast.makeText(ScheduleFeed.this, "Timer Added",
                            Toast.LENGTH_SHORT).show();
                    dbHandler.addNewTimer(ID, timerName, date, time, amount, notes);

                }

                else if (dbHandler.checkID(ID)){
                    while (dbHandler.checkID(ID)){
                    ++i;
                    ID = String.valueOf(i);}

                    Toast.makeText(ScheduleFeed.this, "Timer Added",
                            Toast.LENGTH_SHORT).show();

                    dbHandler.addNewTimer(ID, timerName, date, time, amount, notes);
                    return;
                }
                else {
                    dbHandler.addNewTimer(ID, timerName, date, time, amount, notes);
                }
            }
            catch (Exception e){
                Toast.makeText(ScheduleFeed.this, "Something went wrong; try again.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(ScheduleFeed.this, "Timer Added",
                    Toast.LENGTH_SHORT).show();

            enterTimerNameEditText.setText("");
            enterDateViewText.setText("");
            timeTextView.setText("");
            noteEditText.setText("");
            amountEditText.setText("");
        });

        viewButton.setOnClickListener(v -> {
            try {
                Intent i = new Intent(ScheduleFeed.this, ViewTimer.class);
                startActivity(i);
            }
            catch (Exception e){
                Toast.makeText(ScheduleFeed.this, "Something went wrong; try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openNewActivity () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

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
                timeTextView.setText
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
        enterDateViewText.setText(selectedDate);
    }
}
