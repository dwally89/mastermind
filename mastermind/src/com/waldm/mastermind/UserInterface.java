package com.waldm.mastermind;

public interface UserInterface {
    void alertGameStarting();
    void displayWelcomeMessage();
    boolean askIfGuessWasCorrect(String guess);
    void informIncorrectLength(int codeLength);
    void informResult(Result result);
    void informNumberOfGuessesLeft(int numberOfGuessesLeft);
    void informGameOver(String playerName, String code, boolean playerWon, int numberOfGuessesPlayed);
    String askHumanForCode();
}
