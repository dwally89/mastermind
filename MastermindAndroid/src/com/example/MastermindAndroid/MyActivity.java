package com.example.MastermindAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.waldm.mastermind.*;

public class MyActivity extends Activity implements UserInterface {
    private final int CODE_LENGTH = 4;
    private final char[] ALPHABET = new char[]{'R', 'O', 'Y', 'G', 'B', 'P'};
    private int MAXIMUM_NUMBER_OF_GUESSES = 12;
    private Player guesser;
    private CodeCreator codeCreator;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        displayWelcomeMessage();

        guesser = new HumanPlayer(this);
        codeCreator = new ComputerCodeCreator(CODE_LENGTH, ALPHABET);

        Mastermind mastermind = new Mastermind(getCodeLength(), getAlphabet(), getMaximumNumberOfGuesses());
        mastermind.setCodeCreator(getCodeCreator());
        mastermind.setGuesser(getGuesser());
        alertGameStarting();
        mastermind.play();
    }

    @Override
    public Player getGuesser() {
        return guesser;
    }

    @Override
    public CodeCreator getCodeCreator() {
        return codeCreator;
    }

    @Override
    public int getMaximumNumberOfGuesses() {
        return MAXIMUM_NUMBER_OF_GUESSES;
    }

    @Override
    public char[] getAlphabet() {
        return ALPHABET;
    }

    @Override
    public int getCodeLength() {
        return CODE_LENGTH;
    }

    @Override
    public void alertGameStarting() {  }

    @Override
    public void displayWelcomeMessage() {
        new AlertDialog.Builder(this).setMessage(R.string.welcome_message).show();
    }

    @Override
    public String getCode() {
        return getCodeCreator().getCode();
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
    public Result calculateResult(String guess) {
        return Result.calculateResult(guess, getCode());
    }

    @Override
    public String requestGuess(int codeLength, char[] alphabet) {
        return "RRRR"; //TODO change this
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
}
