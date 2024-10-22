package edu.psu.ist.monkey.phase;

public final class Lexer {

    private String input;

    /** Current position in input. */
    private int position;

    /** Current reading position in input. */
    private int readPosition; //

    /** Current char under examination. */
    private byte ch;

    public Lexer(String input) {
        this.input = input;
    }

    /** Updates next character and advances our position in the input. */
    public void readChar() {
        if (readPosition >= input.length()) {
            ch = 0;
        } else {
            ch = input.getBytes()[readPosition];
        }
        position = readPosition;
        readPosition = readPosition + 1;
    }

    

}
