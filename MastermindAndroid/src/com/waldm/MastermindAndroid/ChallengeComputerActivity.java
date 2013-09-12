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
import com.google.common.collect.Lists;
import com.waldm.MastermindAndroid.dialogs.ColourPickerDialog;
import com.waldm.MastermindAndroid.views.Peg;
import com.waldm.MastermindAndroid.views.PegRow;
import com.waldm.mastermind.*;

import java.util.List;

public class ChallengeComputerActivity extends Activity implements UserInterface {
    private Mastermind mastermind;
    private List<PegRow> pegRows = Lists.newArrayList();
    private Peg selectedPeg;
    public static final int RESULT_COLOUR_PICKED = 1989;
    private static final int REQUEST_PICK_COLOUR = 1990;
    public static final String NUMBER_OF_AVAILABLE_COLOURS = "NumberOfAvailableColours";
    public static final String IS_FEEDBACK_DIALOG = "IsFeedbackDialog";
    private ColourRepository colourRepository;
    private int numberOfAvailableColours;
    private Button alertFeedback;
    private PegRow currentRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("");

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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newGame() {
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.removeAllViews();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int codeLength = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_CODE_LENGTH,
                getString(R.string.pref_codeLength_default)));
        final int maximumNumberOfGuesses = 12;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        params.weight = 1;

        createRows(mainLayout, codeLength, maximumNumberOfGuesses, params);

        numberOfAvailableColours = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_NUMBER_OF_COLOURS,
                getString(R.string.pref_numberOfColours_default)));
        this.colourRepository = new ColourRepository(numberOfAvailableColours);


        final char[] alphabet = new char[numberOfAvailableColours];
        List<Colour> colours = colourRepository.getAvailableColours();
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = colours.get(i).shortName;
        }

        mastermind = new Mastermind(codeLength, alphabet, maximumNumberOfGuesses);
        mastermind.setCodeCreator(new HumanCodeCreator(this));
        final CommandLinePlayer player = new ComputerPlayer(codeLength, alphabet);

        alertFeedback = new Button(this);
        alertFeedback.setText(R.string.button_alert_feedback);
        alertFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PegRow currentRow = pegRows.get(pegRows.size() - (mastermind.getNumberOfGuessesPlayed() - 1));
                Result result = currentRow.getFeedbackPegsResult();
                if (result.locationCorrect == codeLength) {
                    createDialog(R.string.computer_player_won);
                    currentRow.removeFeedbackPegsListener();
                    alertFeedback.setEnabled(false);
                } else {
                    player.informResult(result);
                    try {
                        computerMakeGuess(codeLength, alphabet, player);
                    } catch (ComputerPlayer.NoPossibilitiesRemainingException e) {
                        createDialog(R.string.invalid_feedback);
                        newGame();
                    }
                }
            }
        });

        mainLayout.addView(alertFeedback);

        try {
            computerMakeGuess(codeLength, alphabet, player);
        } catch (ComputerPlayer.NoPossibilitiesRemainingException e) {
            // This shouldn't happen the first time
        }
    }

    private void computerMakeGuess(int codeLength, char[] alphabet, CommandLinePlayer player) throws ComputerPlayer.NoPossibilitiesRemainingException {
        String guess = player.requestGuess(codeLength, alphabet);

        List<Peg> guessedPegs = Lists.newArrayList();

        for (char colourShortName : guess.toCharArray()) {
            for (Colour colour : colourRepository.getAvailableColours()) {
                if (colour.shortName == colourShortName) {
                    guessedPegs.add(new Peg(this, colour, null));
                    break;
                }
            }
        }

        if (currentRow != null) {
            currentRow.removeFeedbackPegsListener();
        }

        currentRow = pegRows.get(pegRows.size() - mastermind.getNumberOfGuessesPlayed());
        currentRow.setColours(guessedPegs);
        currentRow.setFeedbackPegsListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPeg = (Peg) view;
                showColourPickerDialog();
            }
        });

        try {
            mastermind.guessCode(guess);
        } catch (Mastermind.IncorrectGuessLengthException e) {
            // THIS SHOULD NEVER HAPPEN
        }
    }

    private void showColourPickerDialog() {
        Intent intent = new Intent(this, ColourPickerDialog.class);
        intent.putExtra(IS_FEEDBACK_DIALOG, true);
        startActivityForResult(intent, REQUEST_PICK_COLOUR);
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
        createDialog(R.string.think_of_a_code);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_COLOUR_PICKED || requestCode != REQUEST_PICK_COLOUR) {
            return;
        }

        String colourName = data.getStringExtra(ColourPickerDialog.COLOUR_KEY);
        if (colourName.equals(Colour.BLACK.name)) {
            selectedPeg.setColour(Colour.BLACK);
        } else if (colourName.equals(Colour.WHITE.name)) {
            selectedPeg.setColour(Colour.WHITE);
        }
    }
}
