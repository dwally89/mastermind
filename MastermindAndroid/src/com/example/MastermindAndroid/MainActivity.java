package com.example.MastermindAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.waldm.mastermind.*;

public class MainActivity extends Activity implements UserInterface {
    private Mastermind mastermind;
    private EditText inputBox;
    private Button guessEntered;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inputBox = (EditText)findViewById(R.id.inputBox);
        guessEntered = (Button) findViewById(R.id.buttonGuessEntered);
        guessEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGuess();
            }
        });

        final int codeLength = 4;
        final char[] alphabet = new char[]{'R', 'O', 'Y', 'G', 'B', 'P'};
        mastermind = new Mastermind(codeLength, alphabet, 12);
        mastermind.setCodeCreator(new ComputerCodeCreator(codeLength, alphabet));
        displayWelcomeMessage();
        alertGameStarting();
    }

    private void handleGuess() {
        String guess = inputBox.getText().toString();

        boolean guessWasCorrect = false;
        try {
            guessWasCorrect = mastermind.guessCode(guess);
        } catch (Mastermind.IncorrectGuessLengthException e) {
            // This can't occur with app UI
        }

        if (guessWasCorrect) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Congratulations, you won!");
            builder.show();
            guessEntered.setEnabled(false);
        } else if (mastermind.getNumberOfGuessesLeft() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sorry, you lost");
            builder.show();
            guessEntered.setEnabled(false);
        } else {
            Result result = mastermind.calculateResult(guess);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You got " + result.locationCorrect + " correct and in the right place and " + result.numberCorrect + " correct but in the wrong place");
            builder.show();
        }

        inputBox.setText("");
    }

    @Override
    public void alertGameStarting() {  }

    @Override
    public void displayWelcomeMessage() {
        new AlertDialog.Builder(this).setMessage(R.string.welcome_message).show();
    }

    @Override
    public boolean askIfGuessWasCorrect(String guess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.question_correct_guess);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO Handle result
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO Handle result
            }
        });

        builder.show();

        return true;
    }

    @Override
    public void informIncorrectLength(int codeLength) {  }

    @Override
    public void informResult(Result result) {
        //TODO
    }

    @Override
    public void informNumberOfGuessesLeft(int numberOfGuessesLeft) {  }

    @Override
    public void informGameOver(String playerName, String code, boolean playerWon, int numberOfGuessesPlayed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = playerName; //TODO Change to string resource
        if (playerWon) {
            message += " won";
        } else {
            message += " did not win";
        }

        message += " the game. The code was: " + code;

        builder.setMessage(message);
        builder.show();
    }

    @Override
    public String askHumanForCode() {
        return null;//TODO
    }
}
