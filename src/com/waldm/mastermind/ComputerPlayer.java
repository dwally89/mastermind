package com.waldm.mastermind;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer implements Player {
    private List<String> possibilities = new ArrayList<String>();

    private ComputerPlayer() {  }

    public ComputerPlayer(int codeLength, char[] alphabet) {
        possibilities = generatePossibilities(codeLength, alphabet);

        for (String possibility : possibilities) {
            System.out.println(possibility);
        }
    }

    private List<String> generatePossibilities(int codeLength, char[] alphabet) {
        List<String> additionalPossibilites = new ArrayList<String>();

        for (char c : alphabet) {
            if (codeLength == 1){
                additionalPossibilites.add(String.valueOf(c));
            } else {
                List<String> evenMorePosibilities = generatePossibilities(codeLength - 1, alphabet);
                for (String possibility : evenMorePosibilities) {
                    additionalPossibilites.add(c + possibility);
                }
            }
        }

        return additionalPossibilites;
    }

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
