package com.example.flashcrd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnClickListener is how Android handles users actions / interaction with the app
        findViewById(R.id.frontQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.frontQuestion).setVisibility(View.INVISIBLE);
                findViewById(R.id.answerQuestion).setVisibility(View.VISIBLE);
            }
        });
    }
}
