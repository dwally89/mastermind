package com.waldm.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanCodeCreator implements CodeCreator {
    @Override
    public String getCode() {
        System.out.println("What was the code? ");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        String code;

        try {
            code = buffer.readLine();
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return code;
    }

    @Override
    public boolean guessWasCorrect(String guess) {
        System.out.println("The guess was: " + guess);
        System.out.println("Was this correct? (Y/N): ");

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        String answer;

        try {
            answer = buffer.readLine();
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return answer.equals("Y");
    }

    @Override
    public Result calculateResult(String guess) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        int rightPosition, wrongPosition;

        try {
            System.out.println("How many were correct and in the right position? ");
            rightPosition = Integer.parseInt(buffer.readLine());
            System.out.println("How many were correct but in the wrong position? ");
            wrongPosition = Integer.parseInt(buffer.readLine());
        } catch (IOException e) {
            throw new RuntimeException("An input error has occurred");
        }

        return new Result(rightPosition, wrongPosition);
    }
}
