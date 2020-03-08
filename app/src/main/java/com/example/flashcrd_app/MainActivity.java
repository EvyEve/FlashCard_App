package com.example.flashcrd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnClickListener is how Android handles users actions / interaction with the app

        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.red));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.red));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        findViewById(R.id.resetAnswers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.orange));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.orange));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.orange));
            }
        });
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            boolean isShowingAnswers = true;
            @Override
            public void onClick(View v) {
                if(isShowingAnswers) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_open_eye);
                    findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                    isShowingAnswers=false;
                }
                else{
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_close_eye);
                    findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                    isShowingAnswers=true;
                }
            }
        });
    }
}
