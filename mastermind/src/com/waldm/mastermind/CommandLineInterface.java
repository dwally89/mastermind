package com.waldm.mastermind;

import java.io.IOException;

public class CommandLineInterface implements UserInterface {
    private final Mastermind mastermind;

    private CommandLineInterface() {
        mastermind = new Mastermind(getCodeLength(), getAlphabet(), getMaximumNumberOfGuesses());
        mastermind.setCodeCreator(getCodeCreator());

        CommandLinePlayer guesser = getGuesser();
        displayWelcomeMessage();
        alertGameStarting();

        while (true) {
            boolean guessIsValid = false;
            boolean guessWasCorrect = false;
            String guess = null;
            while (!guessIsValid) {
                try {
                    guess = guesser.requestGuess(mastermind.codeLength, mastermind.alphabet);
                    guessWasCorrect = mastermind.guessCode(guess);
                    guessIsValid = true;
                } catch (Mastermind.IncorrectGuessLengthException e) {
                    System.out.println("Guesses must contain " + e.codeLength + " characters");
                } catch (ComputerPlayer.NoPossibilitiesRemainingException e) {
                    // TODO Handle this exception
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            if (guessWasCorrect) {
                guesser.informGameOver(mastermind.getCode(), true, mastermind.getNumberOfGuessesPlayed());
            } else if (mastermind.getNumberOfGuessesLeft() == 0) {
                guesser.informGameOver(mastermind.getCode(), false, mastermind.getNumberOfGuessesPlayed());
            } else {
                guesser.informResult(mastermind.calculateResult(guess));
            }
        }
    }

    private CommandLinePlayer getGuesser() {
        System.out.println("Who will be guessing the code?");
        System.out.println("Enter H for human or C for computer");
        String guesserString;
        try {
            guesserString = Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }

        if (guesserString.equals("H")) {
            return new CommandLineHumanPlayer(this);
        } else {
            return new ComputerPlayer(mastermind.codeLength, mastermind.alphabet);
        }
    }

    private CodeCreator getCodeCreator() {
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

    private int getMaximumNumberOfGuesses() {
        System.out.println("What is the maximum number of guesses allowed? ");
        try {
            return Integer.parseInt(Utils.readStringFromConsole());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read from console");
        }
    }

    private char[] getAlphabet() {
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

    private int getCodeLength() {
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

    @Override
    public String askHumanForCode() {
        System.out.println("What is the code? The computer will not be told this...");
        try {
            return Utils.readStringFromConsole();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from console");
        }
    }

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

    public static void main(String[] args) {
        new CommandLineInterface();
    }
}
