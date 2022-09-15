package com.example.petfeeder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SnackFeedActivity extends AppCompatActivity {

    private String snackText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_feed);

        ImageButton dispenseSnack = (ImageButton) findViewById(R.id.dispenseSnack);
        EditText snackEditText = (EditText) findViewById(R.id.snackEditText);


        dispenseSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    snackText = snackEditText.getText().toString();
                    Integer snackNum = Integer.valueOf(snackText);


                    if (snackNum > 100){
                        Toast.makeText(SnackFeedActivity.this, "That's a Big Snack!\n" + snackText + "g Snack Dispensed",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SnackFeedActivity.this, snackText + "g Snack Dispensed",
                                Toast.LENGTH_SHORT).show();
                    }
                    return;
                } catch (Exception e) {
                    Toast.makeText(SnackFeedActivity.this, "Enter an Amount to Dispense",
                            Toast.LENGTH_SHORT).show();

                }
                snackEditText.setText("");
            }
        });
    }
}