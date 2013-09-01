package com.waldm.mastermind;

public class HumanCodeCreator implements CodeCreator {
    private final UserInterface userInterface;
    private final String code;

    public HumanCodeCreator(UserInterface userInterface) {
        this.userInterface = userInterface;
        code = userInterface.askHumanForCode();
    }

    @Override
    public boolean guessWasCorrect(String guess) {
        return userInterface.askIfGuessWasCorrect(guess);
    }

    @Override
    public String getCode() {
        return code;
    }
}
