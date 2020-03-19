package com.example.flashcrd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnClickListener is how Android handles users actions / interaction with the app
        findViewById(R.id.frontQuestion).setOnClickListener(new View.OnClickListener() {
            //OnClickListener to show answer & hide question
            @Override
            public void onClick(View v) {
                findViewById(R.id.frontQuestion).setVisibility(View.INVISIBLE);
                findViewById(R.id.answerQuestion).setVisibility(View.VISIBLE);
            }
        });
        //OnClicklistener to show question & hide answer
        findViewById(R.id.answerQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answerQuestion).setVisibility(View.INVISIBLE);
                findViewById(R.id.frontQuestion).setVisibility(View.VISIBLE);
            }
        });


        findViewById(R.id.choice1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice1).setBackground(getResources().getDrawable(R.drawable.wrong_choice_background));
                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
            }
        });
        findViewById(R.id.choice2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice2).setBackground(getResources().getDrawable(R.drawable.wrong_choice_background));
                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
            }
        });
        findViewById(R.id.choice3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
            }
        });
        //Textview to reset choices back to original look before choice was made
        findViewById(R.id.resetAnswers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.choice1).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
                findViewById(R.id.choice2).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
            }
        });
        //Toggling card choice visibility via ic_open_eye icon & ic_close_eye icon
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            boolean isShowingAnswers = true;
            @Override
            public void onClick(View v) {
                if(isShowingAnswers) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_open_eye);
                    findViewById(R.id.choice1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.choice2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.choice3).setVisibility(View.INVISIBLE);
                    isShowingAnswers=false;
                }
                else{
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_close_eye);
                    findViewById(R.id.choice1).setVisibility(View.VISIBLE);
                    findViewById(R.id.choice2).setVisibility(View.VISIBLE);
                    findViewById(R.id.choice3).setVisibility(View.VISIBLE);
                    isShowingAnswers=true;
                }
            }
        });
        //Adding a new activity / card and Navigating to it
        findViewById(R.id.add_Card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                //Start Next Activity & Specify Data Is Expected To Be Returned
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.edit_Card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                //Grabbing the text from MainActivity textview & storing it in a string
                String questEdit = ((TextView) findViewById(R.id.frontQuestion)).getText().toString();
                String ansEdit = ((TextView) findViewById(R.id.answerQuestion)).getText().toString();
                String wrongEdit1 = ((TextView) findViewById(R.id.choice1)).getText().toString();
                String wrongEdit2 = ((TextView) findViewById(R.id.choice2)).getText().toString();

                //Passing the string data from MainActivity to the intent for the next activity
                intent.putExtra("qEdit", questEdit);
                intent.putExtra("aEdit",ansEdit);
                intent.putExtra("wEdit1", wrongEdit1);
                intent.putExtra("wEdit2", wrongEdit2);

                //Start Next Activity & Specify Data Is Expected To Be Returned
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }
    //Method to get & store data from previous activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Storing String values from previous activity
            String question = data.getExtras().getString("questString");
            String answer = data.getExtras().getString("anString");
            String wrgChoice1 = data.getExtras().getString("wrongCh1");
            String wrgChoice2 = data.getExtras().getString("wrongCh2");

            ((TextView) findViewById(R.id.frontQuestion)).setText(question);
            ((TextView) findViewById(R.id.answerQuestion)).setText(answer);
            ((TextView) findViewById(R.id.choice1)).setText(wrgChoice1);
            ((TextView) findViewById(R.id.choice2)).setText(wrgChoice2);
            ((TextView) findViewById(R.id.choice3)).setText(answer);
        }
        //Snackbar display message once card has been successfully created
        Snackbar.make(findViewById(R.id.frontQuestion),
                "Card Successfully Created!",
                Snackbar.LENGTH_SHORT).show();
    }
}
