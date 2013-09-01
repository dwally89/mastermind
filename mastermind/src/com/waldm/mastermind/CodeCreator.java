package com.waldm.mastermind;

public interface CodeCreator {
    boolean guessWasCorrect(String guess);
    String getCode();
}
