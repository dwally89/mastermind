package com.waldm.mastermind;

public interface UserInterface {
    Player getGuesser();
    CodeCreator getCodeCreator();
    int getMaximumNumberOfGuesses();
    char[] getAlphabet();
    int getCodeLength();
    void alertGameStarting();
    void displayWelcomeMessage();
    String getCode();
    boolean askIfGuessWasCorrect(String guess);
    Result calculateResult(String guess);
    String requestGuess(int codeLength, char[] alphabet);
    void informIncorrectLength(int codeLength);
    void informResult(Result result);
    void informNumberOfGuessesLeft(int numberOfGuessesLeft);
    void informGameOver(String playerName, String code, boolean playerWon, int numberOfGuessesPlayed);
}
