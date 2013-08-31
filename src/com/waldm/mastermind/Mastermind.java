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

            if (guess.equals(code)) {
                System.out.println("You guessed correctly!");
                break;
            }

            int locationCorrect = 0;
            int numberCorrect = 0;
            for(int i = 0; i < CODE_LENGTH; i++) {
                if(guess.charAt(i) == code.charAt(i)) {
                    locationCorrect++;
                } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                    numberCorrect++;
                }
            }

            System.out.println(createFeedbackMessage(locationCorrect, numberCorrect));

            currentGuess++;
        }

        System.out.println("Game over, you lost, sorry :-(");
    }

    private static String createFeedbackMessage(int locationCorrect, int numberCorrect) {
        String feedbackMessage = "There ";

        if (locationCorrect == 1) {
            feedbackMessage += "is";
        } else {
            feedbackMessage += "are";
        }

        feedbackMessage += " " + locationCorrect + " correct number";
        if (locationCorrect != 1) {
            feedbackMessage += "s";
        }

        feedbackMessage += " in the correct location and there ";

        if(numberCorrect == 1) {
            feedbackMessage += "is";
        } else {
            feedbackMessage += "are";
        }

        feedbackMessage += " " + numberCorrect + " correct number";

        if(numberCorrect != 1) {
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
}