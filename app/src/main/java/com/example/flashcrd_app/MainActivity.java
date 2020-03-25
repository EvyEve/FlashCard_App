package com.example.flashcrd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Database variable - Instance of database to allow both read and write to database.
    FlashcardDatabase flashcardDatabase;

    // Variable to hold / store list of flashcard objects
    List<Flashcard> allFlashCards;

    //Variable to keep track of current card index
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize local flashcard database variable
        flashcardDatabase = new FlashcardDatabase(this);
        //Get all of the flashcards saved in the database
        allFlashCards = flashcardDatabase.getAllCards();

        // Checks To See If Database Is Empty. If Not Return / Display An Empty State
        if(allFlashCards.isEmpty()){
            ((TextView) findViewById(R.id.flashQuestion)).setText("Welcome! \n Add A Flash Card To Begin!");
            ((TextView) findViewById(R.id.flashAnswer)).setText("Your Flash Card Answers Will Go Here!");
        }
        // Adds Scrolling Capability to TextViews
        ((TextView) findViewById(R.id.flashQuestion)).setMovementMethod(new ScrollingMovementMethod());
        ((TextView) findViewById(R.id.flashAnswer)).setMovementMethod(new ScrollingMovementMethod());

        //Checks to see if database is empty. If not return / display last saved flashcard
        if (allFlashCards != null && allFlashCards.size() > 0) {
            ((TextView) findViewById(R.id.flashQuestion)).setText(allFlashCards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashAnswer)).setText(allFlashCards.get(0).getAnswer());
        }

        //OnClickListener is how Android handles users actions / interaction with the app
        findViewById(R.id.flashQuestion).setOnClickListener(new View.OnClickListener() {
            //OnClickListener to show answer & hide question
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashQuestion).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashAnswer).setVisibility(View.VISIBLE);
            }
        });

        //OnClicklistener to show question & hide answer
        findViewById(R.id.flashAnswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashAnswer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashQuestion).setVisibility(View.VISIBLE);
            }
        });
//
//
//        findViewById(R.id.choice1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.choice1).setBackground(getResources().getDrawable(R.drawable.wrong_choice_background));
//                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
//            }
//        });
//        findViewById(R.id.choice2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.choice2).setBackground(getResources().getDrawable(R.drawable.wrong_choice_background));
//                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
//            }
//        });
//        findViewById(R.id.choice3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.correct_choice_background));
//            }
//        });
//        //Textview to reset choices back to original look before choice was made
//        findViewById(R.id.resetAnswers).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                findViewById(R.id.choice1).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
//                findViewById(R.id.choice2).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
//                findViewById(R.id.choice3).setBackground(getResources().getDrawable(R.drawable.choices_backgroung));
//            }
//        });

        // Browse Through Previously Created Flash Cards In Local Database
        findViewById(R.id.next_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast Error Message For User To Add A Flash Card To The Database if Flashcard database is empty
                if (allFlashCards.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Add A Card", Toast.LENGTH_SHORT);
                    TextView tstView = (TextView) toast.getView().findViewById(android.R.id.message);
                    tstView.setTextColor(Color.WHITE);
                    tstView.setTypeface(null, Typeface.BOLD);
                    toast.getView().setBackground(getResources().getDrawable(R.drawable.toast));
                    toast.setGravity(0, 1, 600);
                    toast.show();
                }

                // Toast Error Message For User To Add Another Flash Card To The Database if Only One Is Currently in Database
                else if (allFlashCards.size() == 1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "There Is Only One Card, Add Another Card", Toast.LENGTH_SHORT);
                    TextView tstView = (TextView) toast.getView().findViewById(android.R.id.message);
                    tstView.setTextColor(Color.WHITE);
                    tstView.setTypeface(null, Typeface.BOLD);
                    toast.getView().setBackground(getResources().getDrawable(R.drawable.toast));
                    toast.setGravity(0, 1, 600);
                    toast.show();
                }

                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashCards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // Shows Next Flash Card If There Is More Than One Card In Database
                if(allFlashCards.size() > 1) {
                    // set the question and answer TextViews with data from the database
                    ((TextView) findViewById(R.id.flashQuestion)).setText(allFlashCards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashAnswer)).setText(allFlashCards.get(currentCardDisplayedIndex).getAnswer());
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
        // Edit a card that has already been created
        findViewById(R.id.edit_Card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                //Grabbing the text from MainActivity textview & storing it in a string
                String questEdit = ((TextView) findViewById(R.id.flashQuestion)).getText().toString();
                String ansEdit = ((TextView) findViewById(R.id.flashAnswer)).getText().toString();
//                String wrongEdit1 = ((TextView) findViewById(R.id.choice1)).getText().toString();
//                String wrongEdit2 = ((TextView) findViewById(R.id.choice2)).getText().toString();

                //Passing the string data from MainActivity to the intent for the next activity
                intent.putExtra("qEdit", questEdit);
                intent.putExtra("aEdit",ansEdit);
//                intent.putExtra("wEdit1", wrongEdit1);
//                intent.putExtra("wEdit2", wrongEdit2);

                //Start Next Activity & Specify Data Is Expected To Be Returned
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
        // Deletes Previously Created Flash Cards in Local Database
        findViewById(R.id.trash_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(allFlashCards.size() > 1) {
                    String deleteQuestion = ((TextView) findViewById(R.id.flashQuestion)).getText().toString();
                    currentCardDisplayedIndex = allFlashCards.indexOf(deleteQuestion);

                    if(currentCardDisplayedIndex < 1){
                        flashcardDatabase.deleteCard(deleteQuestion);
                        allFlashCards = flashcardDatabase.getAllCards();
                        currentCardDisplayedIndex++; // Moves current index foward by 1

                        // set the question and answer TextViews with data from the database
                        ((TextView) findViewById(R.id.flashQuestion)).setText(allFlashCards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashAnswer)).setText(allFlashCards.get(currentCardDisplayedIndex).getAnswer());
                    }
                    else{
                        flashcardDatabase.deleteCard(deleteQuestion);
                        allFlashCards = flashcardDatabase.getAllCards();
                        currentCardDisplayedIndex--; // Subtracts current index by 1

                        // set the question and answer TextViews with data from the database
                        ((TextView) findViewById(R.id.flashQuestion)).setText(allFlashCards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashAnswer)).setText(allFlashCards.get(currentCardDisplayedIndex).getAnswer());
                    }
//                    allFlashCards = flashcardDatabase.getAllCards();
//                    // set the question and answer TextViews with data from the database
//                    ((TextView) findViewById(R.id.flashQuestion)).setText(allFlashCards.get(currentCardDisplayedIndex).getQuestion());
//                    ((TextView) findViewById(R.id.flashAnswer)).setText(allFlashCards.get(currentCardDisplayedIndex).getAnswer());
                }
                // Sets an Empty State For When All Cards Have Been Deleted
                else{
                    String deleteQuestion = ((TextView) findViewById(R.id.flashQuestion)).getText().toString();
                    flashcardDatabase.deleteCard(deleteQuestion);
                    allFlashCards = flashcardDatabase.getAllCards();
                    currentCardDisplayedIndex--;

                    ((TextView) findViewById(R.id.flashQuestion)).setText("To Begin (+)\n Please Add A Card!");
                    ((TextView)findViewById(R.id.flashAnswer)).setText("Please Add An Answer For Your Card");
                }
                // Toast Message To Alert User There Are No More Cards To Delete & To Add A Card
                if(allFlashCards.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), "No Cards To Delete. (+) Add A Card!", Toast.LENGTH_SHORT);
                    TextView tstView = toast.getView().findViewById(android.R.id.message);
                    tstView.setTextColor(Color.WHITE);
                    tstView.setTypeface(null, Typeface.BOLD);
                    toast.getView().setBackground(getResources().getDrawable(R.drawable.toast));
                    toast.setGravity(0, 1, 600);
                    toast.show();
                }
                allFlashCards = flashcardDatabase.getAllCards();
            }
        });

//        //Toggling card choice visibility via ic_open_eye icon & ic_close_eye icon
//        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
//            boolean isShowingAnswers = true;
//            @Override
//            public void onClick(View v) {
//                if(isShowingAnswers) {
//                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_open_eye);
//                    findViewById(R.id.choice1).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.choice2).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.choice3).setVisibility(View.INVISIBLE);
//                    isShowingAnswers=false;
//                }
//                else{
//                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_close_eye);
//                    findViewById(R.id.choice1).setVisibility(View.VISIBLE);
//                    findViewById(R.id.choice2).setVisibility(View.VISIBLE);
//                    findViewById(R.id.choice3).setVisibility(View.VISIBLE);
//                    isShowingAnswers=true;
//                }
//            }
//        });

    }
    //Method to get & store data from previous activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //Storing String values from previous activity
            String question = data.getExtras().getString("questString");
            String answer = data.getExtras().getString("anString");
//            String wrgChoice1 = data.getExtras().getString("wrongCh1");
//            String wrgChoice2 = data.getExtras().getString("wrongCh2");

            //Saves newly created flashcard data into local database
            flashcardDatabase.insertCard(new Flashcard(question,answer));

            // Updates list of flashcards
            allFlashCards = flashcardDatabase.getAllCards();

            //Sets created flashcard values to mainActivity textfields
            ((TextView) findViewById(R.id.flashQuestion)).setText(question);
            ((TextView) findViewById(R.id.flashAnswer)).setText(answer);
//            ((TextView) findViewById(R.id.choice1)).setText(wrgChoice1);
//            ((TextView) findViewById(R.id.choice2)).setText(wrgChoice2);
//            ((TextView) findViewById(R.id.choice3)).setText(answer);
           int duration=-2;
            //Snackbar display message once card has been successfully created
            Snackbar.make(findViewById(R.id.flashQuestion),
                    "Card Successfully Created!",
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}
