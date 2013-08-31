package com.waldm.mastermind;

public class ComputerPlayer implements Player {
    @Override
    public String requestGuess(int codeLength, char[] alphabet) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void informIncorrectLength(int codeLength) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void informCorrectGuess() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void informResult(Result result) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void informNumberOfGuessesLeft(int numberOfGuessesLeft) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void informGameOver(String code) {
        throw new RuntimeException("Not implemented yet");
    }
}
