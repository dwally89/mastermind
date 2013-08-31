package com.waldm.mastermind;

public interface Player {
    String requestGuess(int codeLength, char[] alphabet);
    void informIncorrectLength(int codeLength);
    void informCorrectGuess();
    void informResult(Result result);
    void informNumberOfGuessesLeft(int numberOfGuessesLeft);
    void informGameOver(String code);
}
