package com.waldm.mastermind;

public class HumanCodeCreator implements CodeCreator {
    private final UserInterface userInterface;

    public HumanCodeCreator(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public String getCode() {
        return userInterface.askForCode();
    }

    @Override
    public boolean guessWasCorrect(String guess) {
        return userInterface.askIfGuessWasCorrect(guess);
    }

    @Override
    public Result calculateResult(String guess) {
        return userInterface.calculateResult(guess);
    }
}
