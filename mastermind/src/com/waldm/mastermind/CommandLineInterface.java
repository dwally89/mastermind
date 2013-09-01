package com.waldm.mastermind;

import java.io.IOException;

public class CommandLineInterface implements UserInterface {
    private final Mastermind mastermind;

    public CommandLineInterface() {
        displayWelcomeMessage();
        mastermind = new Mastermind(getCodeLength(), getAlphabet(), getMaximumNumberOfGuesses());
        mastermind.setCodeCreator(getCodeCreator());
        mastermind.setGuesser(getGuesser());
        alertGameStarting();
        mastermind.play();
    }

    @Override
    public Player getGuesser() {
        System.out.println("Who will be guessing the code?");
        System.out.println("Enter H for human or C for computer");
        String guesserString;
        try {
            guesserString = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }

        if (guesserString.equals("H")) {
            return new HumanPlayer(this);
        } else {
            return new ComputerPlayer(mastermind.codeLength, mastermind.alphabet);
        }
    }

    @Override
    public CodeCreator getCodeCreator() {
        System.out.println("Who will be creating the code?");
        System.out.println("Enter H for human or C for computer");
        String codeCreatorString;
        try {
            codeCreatorString = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }

        if (codeCreatorString.equals("H")) {
            return new HumanCodeCreator(this);
        } else {
            return new ComputerCodeCreator(mastermind.codeLength, mastermind.alphabet);
        }
    }

    @Override
    public int getMaximumNumberOfGuesses() {
        System.out.println("What is the maximum number of guesses allowed? ");
        try {
            return Integer.parseInt(Utils.readStringFromConsole());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }
    }

    @Override
    public char[] getAlphabet() {
        System.out.println("What letters/numbers can the code contain?");
        System.out.println("Enter the letters/numbers as a continuous string, with no spaces or commas");
        String alphabetString;
        try {
            alphabetString = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }

        char[] newAlphabet = new char[alphabetString.length()];
        for (int i = 0; i < alphabetString.length(); i++) {
            newAlphabet[i] = alphabetString.charAt(i);
        }

        return newAlphabet;
    }

    @Override
    public int getCodeLength() {
        System.out.println("How long should the code be? ");
        try {
            return Integer.parseInt(Utils.readStringFromConsole());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }
    }

    @Override
    public void alertGameStarting() {
        System.out.println("Game starting!");
    }

    @Override
    public void displayWelcomeMessage() {
        System.out.println("Welcome to Mastermind!");
    }

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
    public boolean askIfGuessWasCorrect(String guess) {
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

    @Override
    public String requestGuess(int codeLength, char[] alphabet) {
        String alphabetString = "[";
        for (int i = 0; i < alphabet.length; i++) {
            if (i != 0) {
                alphabetString += ", ";
            }
            alphabetString += alphabet[i];
        }
        alphabetString += "]";

        System.out.println("Choose a " + codeLength + " character code, permissible characters being: " + alphabetString + ": ");

        String guess;
        try {
            guess = Utils.readStringFromConsole();
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
        String message = numberOfGuessesLeft + " guess";
        if (numberOfGuessesLeft > 1) {
            message += "es";
        }

        System.out.println(message + " left\n");
    }

    @Override
    public void informGameOver(String playerName, String code, boolean playerWon, int numberOfGuessesPlayed) {
        if (playerWon) {
            System.out.println("Congratulations, " + playerName + " won in " + numberOfGuessesPlayed + " guesses!");
        } else {
            System.out.println("Game over, " + playerName + " lost, sorry :-(, the code was  " + code);
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        new CommandLineInterface();
    }
}
