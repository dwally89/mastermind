package com.waldm.mastermind;

public class CommandLineHumanPlayer implements CommandLinePlayer {
    private final CommandLineInterface userInterface;

    public CommandLineHumanPlayer(CommandLineInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public String requestGuess(int codeLength, char[] alphabet) {
        return userInterface.requestGuess(codeLength, alphabet);
    }

    @Override
    public void informIncorrectLength(int codeLength) {
        userInterface.informIncorrectLength(codeLength);
    }

    @Override
    public void informResult(Result result) {
        userInterface.informResult(result);
    }

    @Override
    public void informNumberOfGuessesLeft(int numberOfGuessesLeft) {
        userInterface.informNumberOfGuessesLeft(numberOfGuessesLeft);
    }

    @Override
    public void informGameOver(String code, boolean playerWon, int numberOfGuessesPlayed) {
        userInterface.informGameOver("you", code, playerWon, numberOfGuessesPlayed);
    }
}
