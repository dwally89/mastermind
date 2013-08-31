package com.waldm.mastermind;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer implements Player {
    private List<String> possibilities = new ArrayList<String>();
    private String currentGuess;

    public ComputerPlayer(int codeLength, char[] alphabet) {
        possibilities = generatePossibilities(codeLength, alphabet);
    }

    private List<String> generatePossibilities(int codeLength, char[] alphabet) {
        List<String> additionalPossibilities = new ArrayList<String>();

        for (char c : alphabet) {
            if (codeLength == 1){
                additionalPossibilities.add(String.valueOf(c));
            } else {
                List<String> evenMorePossibilities = generatePossibilities(codeLength - 1, alphabet);
                for (String possibility : evenMorePossibilities) {
                    additionalPossibilities.add(c + possibility);
                }
            }
        }

        return additionalPossibilities;
    }

    @Override
    public String requestGuess(int codeLength, char[] alphabet) {
        currentGuess = possibilities.remove(0);
        System.out.println("Guess: " + currentGuess);
        return currentGuess;
    }

    @Override
    public void informIncorrectLength(int codeLength) {
        throw new RuntimeException("Guess of incorrect length. This should never occur with a ComputerPlayer");
    }

    @Override
    public void informCorrectGuess() {
        System.out.println("ComputerPlayer guessed correctly");
    }

    @Override
    public void informResult(final Result result) {
        List<String> newPossibilities = new ArrayList<String>();
        Result possibilityResult;
        for (String possibility : possibilities) {
            possibilityResult = Result.calculateResult(possibility, currentGuess);

            if (possibilityResult.locationCorrect == result.locationCorrect && possibilityResult.numberCorrect == result.numberCorrect) {
                newPossibilities.add(possibility);
            }
        }

        possibilities = newPossibilities;
    }

    @Override
    public void informNumberOfGuessesLeft(int numberOfGuessesLeft) {  }

    @Override
    public void informGameOver(String code) {
        System.out.println("ComputerPlayer lost the game. The code was: " + code);
    }
}
