package com.waldm.mastermind;

import java.io.IOException;

public class HumanCodeCreator implements CodeCreator {
    @Override
    public String getCode() {
        System.out.println("What was the code? ");
        String code;

        try {
            code = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return code;
    }

    @Override
    public boolean guessWasCorrect(String guess) {
        System.out.println("The guess was: " + guess);
        System.out.println("Was this correct? (Y/N): ");

        String answer;
        try {
            answer = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return answer.equals("Y");
    }

    @Override
    public Result calculateResult(String guess) {
        int rightPosition, wrongPosition;

        try {
            System.out.println("How many were correct and in the right position? ");
            rightPosition = Integer.parseInt(Utils.readStringFromConsole());
            System.out.println("How many were correct but in the wrong position? ");
            wrongPosition = Integer.parseInt(Utils.readStringFromConsole());
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return new Result(rightPosition, wrongPosition);
    }
}
