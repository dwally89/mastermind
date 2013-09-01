package com.waldm.mastermind;

public interface CommandLinePlayer extends Player{
    void informIncorrectLength(int codeLength);
    void informNumberOfGuessesLeft(int numberOfGuessesLeft);
    void informResult(Result result);
    void informGameOver(String code, boolean playerWon, int numberOfGuessesPlayed);
    String requestGuess(int codeLength, char[] alphabet);
}
