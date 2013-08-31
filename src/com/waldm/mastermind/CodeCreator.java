package com.waldm.mastermind;

public interface CodeCreator {
    Result calculateResult(String guess);
    boolean guessWasCorrect(String guess);
    String getCode();
}
