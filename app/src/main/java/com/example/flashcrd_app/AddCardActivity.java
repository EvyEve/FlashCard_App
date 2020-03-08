package com.example.flashcrd_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.save_Card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Grabbing Text From Edit Text Field & storing values into string variables
                String question = ((EditText) findViewById(R.id.questionText)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerText)).getText().toString();
                //Putting Strings Into Intent To Be Passed To Next Activity
                Intent data = new Intent(); //create a new Intent to store String data
                data.putExtra("questString", question); //Pass Strings to new Intent, one is Key, other is value
                data.putExtra("anString", answer); ////Pass Strings to new Intent, one is Key, other is value
                setResult(RESULT_OK, data); //set result code and bundle data for response
                finish(); //Close current activity & pass along data to previous activity that launched this activity
            }
        });

        findViewById(R.id.cancel_Card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(intent);
                finish(); //Close / Dismiss current activity
            }
        });
    }
}
