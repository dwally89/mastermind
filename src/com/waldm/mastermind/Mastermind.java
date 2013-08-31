package com.waldm.mastermind;

import java.io.*;
import java.util.Random;

public class Mastermind {
    public static void main (String[] args)
    {
        final int MINIMUM_NUMBER = 1;
        final int MAXIMUM_NUMBER = 8;
        final int CODE_LENGTH = 4;
        final int MAXIMUM_NUMBER_OF_GUESSES = 10;

        final String code = generateCode(MINIMUM_NUMBER, MAXIMUM_NUMBER, CODE_LENGTH);

        int currentGuess = 1;
        while (currentGuess < MAXIMUM_NUMBER_OF_GUESSES) {
            System.out.println("Choose a " + CODE_LENGTH + " digit code, each digit between " + MINIMUM_NUMBER + "-" + MAXIMUM_NUMBER + ": ");

            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader buffer = new BufferedReader(isr);
            String guess = null;

            try {
                guess = buffer.readLine();
            } catch (IOException e) {
                throw new RuntimeException("An input error has occurred");
            }

            if (guess.length() != CODE_LENGTH) {
                System.out.println("Make sure you enter " + CODE_LENGTH + " numbers");
            } else {
                if (guess.equals(code)) {
                    System.out.println("You guessed correctly!");
                    break;
                }

                Result result = calculateResult(guess, code);

                System.out.println(createFeedbackMessage(result));

                currentGuess++;
            }
        }

        System.out.println("Game over, you lost, sorry :-(, the code was  " + code);
    }

    private static Result calculateResult(String guess, String code) {
        int locationCorrect = 0;
        int numberCorrect = 0;
        for(int i = 0; i < code.length(); i++) {
            if(guess.charAt(i) == code.charAt(i)) {
                locationCorrect++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                numberCorrect++;
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

        feedbackMessage += " in the correct location and there ";

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

    private static String generateCode(int min, int max, int length) {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < length; i++) {
            code += String.valueOf(rand.nextInt((max - min) + 1) + min);
        }

        return code;
    }

    private static class Result {
        public final int locationCorrect;
        public final int numberCorrect;

        public Result(int locationCorrect, int numberCorrect) {
            this.locationCorrect = locationCorrect;
            this.numberCorrect = numberCorrect;
        }
    }
}