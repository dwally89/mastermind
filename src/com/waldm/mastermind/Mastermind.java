package com.waldm.mastermind;

import java.io.*;
import java.util.Random;

public class Mastermind {
    private final int codeLength;
    private final int minimumNumber;
    private final int maximumNumber;
    private final int maximumNumberOfGuesses;
    private final String code;
    private final Player player;

    public Mastermind(int codeLength, int minimumNumber, int maximumNumber, int maximumNumberOfGuesses, Player player) {
        this.codeLength = codeLength;
        this.minimumNumber = minimumNumber;
        this.maximumNumber = maximumNumber;
        this.maximumNumberOfGuesses = maximumNumberOfGuesses;
        this.player = player;
        this.code = generateCode();
    }

    public static void main (String[] args)
    {
        Mastermind mastermind = new Mastermind(4, 1, 6, 10, new HumanPlayer());
        mastermind.play();
    }

    private void play() {
        int currentGuess = 1;
        while (currentGuess < maximumNumberOfGuesses) {
            String guess = player.requestGuess(codeLength, minimumNumber, maximumNumber);

            if (guess.length() != code.length()) {
                player.informIncorrectLength(code.length());
                continue;
            }

            if (guess.equals(code)) {
                player.informCorrectGuess();
                System.exit(0);
            }

            player.informResult(calculateResult(guess, code));

            int numberOfGuessesLeft = maximumNumberOfGuesses - currentGuess - 1;
            if (numberOfGuessesLeft > 0) {
                player.informNumberOfGuessesLeft(numberOfGuessesLeft);
            }

            currentGuess++;
        }

        player.informGameOver(code);
    }

    private String generateCode() {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += String.valueOf(rand.nextInt((maximumNumber - minimumNumber) + 1) + minimumNumber);
        }

        return code;
    }

    public static Result calculateResult(String guess, String code) {
        int locationCorrect = 0;
        int numberCorrect = 0;

        boolean[] charMatchedInCode = new boolean[code.length()];

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
}
