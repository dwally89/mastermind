package com.waldm.mastermind;

public class Mastermind {
    public final int codeLength;
    public final char[] alphabet;
    private final int maximumNumberOfGuesses;
    private CodeCreator codeCreator;
    private Player guesser;

    public Mastermind(int codeLength, char[] alphabet, int maximumNumberOfGuesses) {
        this.codeLength = codeLength;
        this.alphabet = alphabet;
        this.maximumNumberOfGuesses = maximumNumberOfGuesses;
    }

    public void play() {
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

    public void setCodeCreator(CodeCreator codeCreator) {
        this.codeCreator = codeCreator;
    }

    public void setGuesser(Player guesser) {
        this.guesser = guesser;
    }
}
