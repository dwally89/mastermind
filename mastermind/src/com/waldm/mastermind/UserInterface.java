package com.waldm.mastermind;

public interface UserInterface {
    Player askForGuesser();
    CodeCreator askForCodeCreator();
    int askForMaximumNumberOfGuesses();
    char[] askForAlphabet();
    int askForCodeLength();
    void alertGameStarting();
    void displayWelcomeMessage();
    String askForCode();
    boolean askIfGuessWasCorrect(String guess);
    Result calculateResult(String guess);
    String requestGuess(int codeLength, char[] alphabet);
    void informIncorrectLength(int codeLength);
    void informResult(Result result);
    void informNumberOfGuessesLeft(int numberOfGuessesLeft);
    void informGameOver(String playerName, String code, boolean playerWon, int numberOfGuessesPlayed);
}
