package com.waldm.mastermind;

import java.util.Random;

public class ComputerCodeCreator implements CodeCreator{
    private final String code;
    private final int codeLength;
    private final char[] alphabet;

    public ComputerCodeCreator(int codeLength, char[] alphabet) {
        this.codeLength = codeLength;
        this.alphabet = alphabet;
        this.code = generateCode();
    }

    @Override
    public boolean guessWasCorrect(String guess) {
        return guess.equals(code);
    }

    @Override
    public String getCode() {
        return code;
    }

    private String generateCode() {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += alphabet[rand.nextInt(alphabet.length)];
        }

        return code;
    }
}
