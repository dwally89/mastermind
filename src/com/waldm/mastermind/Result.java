package com.waldm.mastermind;

public class Result {
    public final int locationCorrect;
    public final int numberCorrect;

    public Result(int locationCorrect, int numberCorrect) {
        this.locationCorrect = locationCorrect;
        this.numberCorrect = numberCorrect;
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
