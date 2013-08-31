package com.waldm.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer implements Player {
    @Override
    public String requestGuess(int codeLength, int minimumNumber, int maximumNumber) {
        System.out.println("Choose a " + codeLength + " digit code, each digit between " + minimumNumber + "-" + maximumNumber + ": ");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        String guess = null;

        try {
            guess = buffer.readLine();
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return guess;
    }

    @Override
    public void informIncorrectLength(int codeLength) {
        System.out.println("Make sure you enter " + codeLength + " numbers");
    }

    @Override
    public void informCorrectGuess() {
        System.out.println("You guessed correctly!");
    }

    @Override
    public void informResult(Result result) {
        String feedbackMessage = "There ";

        if (result.locationCorrect == 1) {
            feedbackMessage += "is";
        } else {
            feedbackMessage += "are";
        }

        feedbackMessage += " " + result.locationCorrect + " correct number";
        if (result.locationCorrect != 1) {
            feedbackMessage += "s";
        }

        feedbackMessage += " in the correct location\nThere ";

        if(result.numberCorrect == 1) {
            feedbackMessage += "is";
        } else {
            feedbackMessage += "are";
        }

        feedbackMessage += " " + result.numberCorrect + " correct number";

        if(result.numberCorrect != 1) {
            feedbackMessage += "s";
        }

        feedbackMessage += " in the wrong location";

        System.out.println(feedbackMessage);
    }

    @Override
    public void informNumberOfGuessesLeft(int numberOfGuessesLeft) {
        System.out.println(numberOfGuessesLeft + " guesses left\n");
    }

    @Override
    public void informGameOver(String code) {
        System.out.println("Game over, you lost, sorry :-(, the code was  " + code);
    }
}
