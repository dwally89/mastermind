package com.waldm.MastermindAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.dialogs.ColourPickerDialog;
import com.waldm.MastermindAndroid.views.Peg;
import com.waldm.MastermindAndroid.views.PegRow;
import com.waldm.mastermind.ComputerCodeCreator;
import com.waldm.mastermind.Mastermind;
import com.waldm.mastermind.Result;
import com.waldm.mastermind.UserInterface;

import java.util.List;

public class MainActivity extends Activity implements UserInterface, Peg.PegClickListener {
    public static ImmutableList<Colour> colours = new ImmutableList.Builder<Colour>()
                                                                        .add(new Colour("Red", 'R', R.drawable.red_peg))
                                                                        .add(new Colour("Orange", 'O', R.drawable.orange_peg))
                                                                        .add(new Colour("Yellow", 'Y', R.drawable.yellow_peg))
                                                                        .add(new Colour("Green", 'G', R.drawable.green_peg))
                                                                        .add(new Colour("Blue", 'B', R.drawable.blue_peg))
                                                                        .add(new Colour("Purple", 'P', R.drawable.purple_peg))
                                                                        .build();
    private Mastermind mastermind;
    private PegRow guessRow;
    private Button guessEntered;
    private List<PegRow> pegRows = Lists.newArrayList();
    private Peg selectedPeg;
    public static final int RESULT_COLOUR_PICKED = 1989;
    private static final int REQUEST_PICK_COLOUR = 1990;
    private static final int REQUEST_CHANGE_SETTINGS = 1991;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("");

        guessEntered = new Button(this);
        guessEntered.setText(R.string.button_guess);
        guessEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGuess();
            }
        });
        guessEntered.setEnabled(false);

        displayWelcomeMessage();
        alertGameStarting();

        newGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                newGame();
                return true;
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_CHANGE_SETTINGS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newGame() {
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.removeAllViews();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int codeLength = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_CODE_LENGTH, "5"));
        final int maximumNumberOfGuesses = 12;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        params.weight = 1;

        createRows(mainLayout, codeLength, maximumNumberOfGuesses, params);

        guessRow = new PegRow(this, createRow(codeLength, Colour.GREY, this), false);
        mainLayout.addView(guessRow, params);
        mainLayout.addView(guessEntered);
        guessEntered.setVisibility(View.VISIBLE);
        guessEntered.setEnabled(false);

        char[] alphabet = new char[colours.size()];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = colours.get(i).shortName;
        }

        mastermind = new Mastermind(codeLength, alphabet, maximumNumberOfGuesses);
        mastermind.setCodeCreator(new ComputerCodeCreator(codeLength, alphabet));
    }

    private void createRows(LinearLayout mainLayout, int codeLength, int maximumNumberOfGuesses, LinearLayout.LayoutParams params) {
        for (int rowIndex = 0; rowIndex < maximumNumberOfGuesses; rowIndex++) {
            PegRow pegRow = new PegRow(this, createRow(codeLength, Colour.NONE, null), true);
            pegRows.add(pegRow);
            mainLayout.addView(pegRow, params);
        }
    }

    private List<Peg> createRow(int codeLength, Colour background, Peg.PegClickListener listener) {
        List<Peg> pegs = Lists.newArrayList();
        for (int i = 0; i < codeLength; i++) {
            pegs.add(new Peg(this, background, listener));
        }
        return pegs;
    }

    private void handleGuess() {
        List<Peg> guessedPegs = guessRow.getPegs();
        String guess = "";
        for (Peg peg : guessedPegs) {
            Colour colour = peg.getColour();
            guess += colour.shortName;
        }

        PegRow currentRow = pegRows.get(pegRows.size() - mastermind.getNumberOfGuessesPlayed());
        currentRow.setColours(guessedPegs);

        boolean guessWasCorrect = false;
        try {
            guessWasCorrect = mastermind.guessCode(guess);
        } catch (Mastermind.IncorrectGuessLengthException e) {
            // This can't occur with app UI
        }

        if (!guessWasCorrect && mastermind.getNumberOfGuessesLeft() == 0) {
            createDialog(R.string.game_over);
            guessEntered.setVisibility(View.GONE);
        } else {
            Result result = mastermind.calculateResult(guess);
            currentRow.setResult(result);
        }

        if (guessWasCorrect) {
            createDialog(R.string.congratulations);
            guessEntered.setVisibility(View.GONE);
        }
    }

    private void createDialog(int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageId);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void alertGameStarting() {  }

    @Override
    public void displayWelcomeMessage() {
        createDialog(R.string.welcome_message);
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
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public String askHumanForCode() {
        return null;//TODO
    }

    @Override
    public void displayColourPicker(Peg peg) {
        selectedPeg = peg;
        Intent intent = new Intent(this, ColourPickerDialog.class);
        startActivityForResult(intent, REQUEST_PICK_COLOUR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_COLOUR_PICKED || requestCode != REQUEST_PICK_COLOUR) {
            return;
        }

        String colourName = data.getStringExtra(ColourPickerDialog.COLOUR_KEY);
        for (Colour colour : colours) {
            if (colour.name.equals(colourName)) {
                selectedPeg.setColour(colour);
                break;
            }
        }

        updateGuessButtonEnabled();
    }

    private void updateGuessButtonEnabled() {
        boolean enabled = true;
        for (Peg peg : guessRow.getPegs()) {
            if (peg.getColour() == Colour.GREY) {
                enabled = false;
                break;
            }
        }

        guessEntered.setEnabled(enabled);
    }
}


