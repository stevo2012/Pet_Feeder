package com.example.petfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageButton scheduleButton, snackButton;
    private Button syncButton, shopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleButton = findViewById(R.id.scheduleButton);
        snackButton = findViewById(R.id.snackButton);
        syncButton = findViewById(R.id.syncButton);
        shopButton = findViewById(R.id.shopButton);

//button that opens ScheduleFeed activity
        scheduleButton.setOnClickListener(v -> openScheduleActivity());
//button that opens SnackFeed activity
        snackButton.setOnClickListener(v -> openSnackActivity());
//button that opens google chrome to help uses shop for cat food
        shopButton.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.chewy.com/b/cat-325"); //form url
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
//button that "syncs" the app to the device by connecting to the devices website
        syncButton.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://www.fakecatfeeder.com/"); //form url
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

            Toast.makeText(MainActivity.this, "NOT A REAL WEBSITE\nFeeder Synced",
                    Toast.LENGTH_SHORT).show();
        });
    }
//open schedule activity method
    public void openScheduleActivity(){
        Intent intent = new Intent(this, ScheduleFeed.class);
        startActivity(intent);
    }
//open snack activity method
    public void openSnackActivity(){
        Intent intent = new Intent(this, SnackFeedActivity.class);
        startActivity(intent);
    }
}

