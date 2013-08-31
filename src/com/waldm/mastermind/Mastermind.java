package com.waldm.mastermind;

import java.io.*;
import java.util.Random;

public class Mastermind {
    private static final int MINIMUM_NUMBER = 1;
    private static final int MAXIMUM_NUMBER = 8;
    private static final int CODE_LENGTH = 4;
    private static final int MAXIMUM_NUMBER_OF_GUESSES = 10;

    public static class Result {
        public final int locationCorrect;
        public final int numberCorrect;

        public Result(int locationCorrect, int numberCorrect) {
            this.locationCorrect = locationCorrect;
            this.numberCorrect = numberCorrect;
        }
    }

    public static void main (String[] args)
    {
        final String code = generateCode();

        int currentGuess = 1;
        while (currentGuess < MAXIMUM_NUMBER_OF_GUESSES) {
            System.out.println("Choose a " + CODE_LENGTH + " digit code, each digit between " + MINIMUM_NUMBER + "-" + MAXIMUM_NUMBER + ": ");

            String guess = getGuess();

            if (guess.length() != CODE_LENGTH) {
                System.out.println("Make sure you enter " + CODE_LENGTH + " numbers");
                continue;
            }

            if (guess.equals(code)) {
                System.out.println("You guessed correctly!");
                break;
            }

            Result result = calculateResult(guess, code);
            System.out.println(createFeedbackMessage(result));

            int numberOfGuessesLeft = MAXIMUM_NUMBER_OF_GUESSES - currentGuess - 1;
            if (numberOfGuessesLeft > 0) {
                System.out.println(numberOfGuessesLeft + " guesses left\n");
            }

            currentGuess++;
        }

        System.out.println("Game over, you lost, sorry :-(, the code was  " + code);
    }

    private static String getGuess() {
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

    private static String generateCode() {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < CODE_LENGTH; i++) {
            code += String.valueOf(rand.nextInt((MAXIMUM_NUMBER - MINIMUM_NUMBER) + 1) + MINIMUM_NUMBER);
        }

        return code;
    }

    public static Result calculateResult(String guess, String code) {
        int locationCorrect = 0;
        int numberCorrect = 0;

        boolean[] charMatchedInCode = new boolean[CODE_LENGTH];

        char currentChar;
        for (int i = 0; i < guess.length(); i++) {
            currentChar = guess.charAt(i);
            if(currentChar == code.charAt(i)) {
                locationCorrect++;
                charMatchedInCode[i] = true;
            } else if (code.contains(String.valueOf(currentChar))) {
                for (int j = 0; j < guess.length(); j++) {
                    // If the character in position j is not the same in both strings, and the
                    // character at position j in the code is equal to our current character
                    // then the number is correct
                    if (j != i && code.charAt(j) != guess.charAt(j) && code.charAt(j) == currentChar && !charMatchedInCode[j]) {
                        numberCorrect++;
                        charMatchedInCode[j] = true;
                        break;
                    }
                }
            }
        }

        return new Result(locationCorrect, numberCorrect);
    }

    private static String createFeedbackMessage(Result result) {
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
        return feedbackMessage;
    }
}
