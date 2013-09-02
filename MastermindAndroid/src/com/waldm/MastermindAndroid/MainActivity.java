package com.waldm.MastermindAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.waldm.MastermindAndroid.views.Peg;
import com.waldm.MastermindAndroid.views.PegRow;
import com.waldm.mastermind.ComputerCodeCreator;
import com.waldm.mastermind.Mastermind;
import com.waldm.mastermind.Result;
import com.waldm.mastermind.UserInterface;

import java.util.*;

public class MainActivity extends Activity implements UserInterface, Peg.PegClickListener {
    public enum Colour {
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        GREY
    }

    public static Map<Colour, Drawable> colourDrawables;
    private Mastermind mastermind;
    private PegRow guessRow;
    private Button guessEntered;
    private List<PegRow> pegRows = new ArrayList<PegRow>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        final int codeLength = 4;
        final int maximumNumberOfGuesses = 12;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        params.weight = 1;

        List<Drawable> backgrounds = createRows(mainLayout, codeLength, maximumNumberOfGuesses, params);

        guessRow = new PegRow(this, backgrounds, this);
        mainLayout.addView(guessRow, params);

        guessEntered = new Button(this);
        guessEntered.setText("Guess"); // TODO Change this to string resource
        guessEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGuess();
            }
        });

        mainLayout.addView(guessEntered);

        colourDrawables = new HashMap<Colour, Drawable>();
        colourDrawables.put(Colour.RED, getResources().getDrawable(R.drawable.red_peg));
        colourDrawables.put(Colour.ORANGE, getResources().getDrawable(R.drawable.orange_peg));
        colourDrawables.put(Colour.YELLOW, getResources().getDrawable(R.drawable.yellow_peg));
        colourDrawables.put(Colour.GREEN, getResources().getDrawable(R.drawable.green_peg));
        colourDrawables.put(Colour.BLUE, getResources().getDrawable(R.drawable.blue_peg));
        colourDrawables.put(Colour.PURPLE, getResources().getDrawable(R.drawable.purple_peg));

        final char[] alphabet = new char[]{'R', 'O', 'Y', 'G', 'B', 'P'};
        mastermind = new Mastermind(codeLength, alphabet, maximumNumberOfGuesses);
        mastermind.setCodeCreator(new ComputerCodeCreator(codeLength, alphabet));
        displayWelcomeMessage();
        alertGameStarting();
    }

    private List<Drawable> createRows(LinearLayout mainLayout, int codeLength, int maximumNumberOfGuesses, LinearLayout.LayoutParams params) {
        List<Drawable> backgrounds = new ArrayList<Drawable>();
        for (int i = 0; i < codeLength; i++) {
            backgrounds.add(getResources().getDrawable(R.drawable.unused_peg));
        }

        for (int rowIndex = 0; rowIndex < maximumNumberOfGuesses; rowIndex++) {
            PegRow pegRow = new PegRow(this, backgrounds);
            pegRows.add(pegRow);
            mainLayout.addView(pegRow, params);
        }
        return backgrounds;
    }

    private void handleGuess() {
        List<Peg> guessedPegs = guessRow.getPegs();
        String guess = "";
        for (Peg peg : guessedPegs) {
            Colour colour = peg.getColour();
            if (colour == Colour.GREY) {
                return;
            }

            guess += colour.name().charAt(0);
        }

        PegRow currentRow = pegRows.get(pegRows.size() - mastermind.getNumberOfGuessesPlayed());
        currentRow.setColours(guessedPegs);

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
            currentRow.setResult(result);
        }
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

    @Override
    public void displayColourPicker(Peg peg) {
        final List<Colour> values = Collections.unmodifiableList(Arrays.asList(Colour.values()));
        int nextIndex = values.indexOf(peg.getColour()) + 1;
        if (nextIndex == values.size()) {
            nextIndex = 0;
        }

        Colour newColour = values.get(nextIndex);
        peg.setColour(newColour, colourDrawables.get(newColour));
    }
}


