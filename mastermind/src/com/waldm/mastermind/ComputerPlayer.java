package com.waldm.mastermind;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class ComputerPlayer implements CommandLinePlayer {
    private List<String> possibilities = Lists.newArrayList();
    private String currentGuess;

    public ComputerPlayer(int codeLength, char[] alphabet) {
        possibilities = generatePossibilities(codeLength, alphabet);
    }

    private List<String> generatePossibilities(int codeLength, char[] alphabet) {
        List<String> additionalPossibilities = Lists.newArrayList();

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
        Random random = new Random();
        currentGuess = possibilities.remove(random.nextInt(possibilities.size()));
        return currentGuess;
    }

    @Override
    public void informIncorrectLength(int codeLength) {
        throw new RuntimeException("Guess of incorrect length. This should never occur with a ComputerPlayer");
    }

    @Override
    public void informResult(final Result result) {
        List<String> newPossibilities = Lists.newArrayList();
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
    public void informGameOver(String code, boolean playerWon, int numberOfGuessesPlayed) {
        /*
        if (playerWon) {
            System.out.println("Computer player won the game in " + numberOfGuessesPlayed + " guesses");
        } else {
            System.out.println("ComputerPlayer lost the game. The code was: " + code);
        }
        */

        System.exit(0);
    }
}
