package com.waldm.mastermind;

public class Mastermind {
    private final int codeLength;
    private final char[] alphabet;
    private final int maximumNumberOfGuesses;
    private final Player guesser;
    private final CodeCreator codeCreator;
    private final UserInterface userInterface;

    public Mastermind() {
        userInterface = new CommandLineInterface();
        userInterface.displayWelcomeMessage();
        codeLength = userInterface.askForCodeLength();
        alphabet = userInterface.askForAlphabet();
        maximumNumberOfGuesses = userInterface.askForMaximumNumberOfGuesses();
        codeCreator = userInterface.askForCodeCreator();
        guesser = userInterface.askForGuesser();

        play();
    }

    private void play() {
        userInterface.alertGameStarting();

        boolean playerWon = false;
        int numberOfGuessesLeft = maximumNumberOfGuesses;
        while (numberOfGuessesLeft > 0) {
            guesser.informNumberOfGuessesLeft(numberOfGuessesLeft);
            String guess = guesser.requestGuess(codeLength, alphabet);

            if (guess.length() != codeLength) {
                guesser.informIncorrectLength(codeLength);
                continue;
            }

            if (codeCreator.guessWasCorrect(guess)) {
                playerWon = true;
                break;
            }

            guesser.informResult(codeCreator.calculateResult(guess));
            numberOfGuessesLeft--;
        }

        guesser.informGameOver(codeCreator.getCode(), playerWon, maximumNumberOfGuesses - numberOfGuessesLeft + 1);
    }

    public static void main (String[] args) {
        new Mastermind();
    }
}
