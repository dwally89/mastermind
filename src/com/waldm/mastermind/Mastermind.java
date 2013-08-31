package com.waldm.mastermind;

public class Mastermind {
    private final int codeLength;
    private final char[] alphabet;
    private final int maximumNumberOfGuesses;
    private final Player guesser;
    private final CodeCreator codeCreator;

    public Mastermind(int codeLength, char[] alphabet, int maximumNumberOfGuesses, Player guesser, CodeCreator codeCreator) {
        this.codeLength = codeLength;
        this.alphabet = alphabet;
        this.maximumNumberOfGuesses = maximumNumberOfGuesses;
        this.guesser = guesser;
        this.codeCreator = codeCreator;
    }

    public static void main (String[] args) {
        final int codeLength = 4;
        final char[] alphabet = new char[]{'1', '2', '3', '4', '5', '6'};
        Player guesser = new ComputerPlayer(codeLength, alphabet);
        CodeCreator codeCreater = new HumanCodeCreator();
        Mastermind mastermind = new Mastermind(codeLength, alphabet, 10, guesser, codeCreater);
        mastermind.play();
    }

    private void play() {
        int currentGuess = 1;
        while (currentGuess < maximumNumberOfGuesses) {
            String guess = guesser.requestGuess(codeLength, alphabet);

            if (guess.length() != codeLength) {
                guesser.informIncorrectLength(codeLength);
                continue;
            }

            if (codeCreator.guessWasCorrect(guess)) {
                guesser.informCorrectGuess();
                System.exit(0);
            }

            guesser.informResult(codeCreator.calculateResult(guess));

            int numberOfGuessesLeft = maximumNumberOfGuesses - currentGuess - 1;
            if (numberOfGuessesLeft > 0) {
                guesser.informNumberOfGuessesLeft(numberOfGuessesLeft);
            }

            currentGuess++;
        }

        guesser.informGameOver(codeCreator.getCode());
    }
}
