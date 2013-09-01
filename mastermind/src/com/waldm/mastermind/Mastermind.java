package com.waldm.mastermind;

public class Mastermind {
    public final int codeLength;
    public final char[] alphabet;
    private final int maximumNumberOfGuesses;
    private int numberOfGuessesLeft;
    private CodeCreator codeCreator;

    public Mastermind(int codeLength, char[] alphabet, int maximumNumberOfGuesses) {
        this.codeLength = codeLength;
        this.alphabet = alphabet;
        this.maximumNumberOfGuesses = maximumNumberOfGuesses;
        this.numberOfGuessesLeft = maximumNumberOfGuesses;
    }

    public boolean guessCode(String guess) throws IncorrectGuessLengthException {
        if (guess.length() != codeLength) {
            throw new IncorrectGuessLengthException(codeLength);
        }

        numberOfGuessesLeft--;
        return codeCreator.guessWasCorrect(guess);
    }

    public void setCodeCreator(CodeCreator codeCreator) {
        this.codeCreator = codeCreator;
    }

    public int getNumberOfGuessesLeft() {
        return numberOfGuessesLeft;
    }

    public Result calculateResult(String guess) {
        return Result.calculateResult(guess, codeCreator.getCode());
    }

    public String getCode() {
        return codeCreator.getCode();
    }

    public int getNumberOfGuessesPlayed() {
        return maximumNumberOfGuesses - numberOfGuessesLeft + 1;
    }

    public class IncorrectGuessLengthException extends Exception {
        public final int codeLength;

        public IncorrectGuessLengthException(int codeLength) {
            this.codeLength = codeLength;
        }
    }
}
