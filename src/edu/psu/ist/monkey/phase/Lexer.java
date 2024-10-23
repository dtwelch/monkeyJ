package edu.psu.ist.monkey.phase;

import edu.psu.ist.monkey.lang.Token;
import io.vavr.collection.List;
import io.vavr.collection.Vector;

public final class Lexer {

    private String input;

    /** Current position in input. */
    private int position;

    /** Current reading position in input. */
    private int readPosition;

    /** Current char under examination. */
    private byte ch;

    public Lexer(String input) {
        this.input = input;
        readChar(); // to ensure lexer is in a fully working state
        // before anyone calls nextToken()
    }

    /** Updates next character and advances our position in the input. */
    public void readChar() {
        if (readPosition >= input.length()) {
            ch = 0;
        } else {
            ch = (byte) input.charAt(readPosition);
        }
        position = readPosition;
        readPosition = readPosition + 1;
    }

    public Token nextToken() {
        skipWhitespace();
        //System.out.println("current char: " + (char) ch);
        Token tok;
        switch (ch) {
            case '=' -> tok = Token.Assign.AssignInst;
            case ';' -> tok = Token.Semi.SemiInst;
            case '(' -> tok = Token.LParen.LParenInst;
            case ')' -> tok = Token.RParen.RParenInst;
            case ',' -> tok = Token.Comma.CommaInst;
            case '+' -> tok = Token.Plus.PlusInst;
            case '{' -> tok = Token.LBrace.LBraceInst;
            case '}' -> tok = Token.RBrace.RBraceInst;
            case 0 -> tok = Token.Eof.EofInst;
            default -> {
                if (isLetter(ch)) {
                    var rawId = readIdentifier();
                    // returns a kw if rawId contains one
                    return Token.fromText(rawId);
                } else if (isDigit(ch)) {
                    return new Token.Num(readNumber());
                } else {
                    return Token.Illegal.IllegalInst;
                }
            }
        }
        readChar();
        return tok;
    }

    private void skipWhitespace() {
        while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
            readChar();
        }
    }

    private String readIdentifier() {
        var a = this.position;
        while (isLetter(ch)) {
            readChar();
        }
        return input.substring(a, this.position);
    }

    private String readNumber() {
        var a = this.position;
        while (isDigit(ch)) {
            readChar();
        }
        return input.substring(a, this.position);
    }

    private boolean isLetter(byte b) {
        return 'a' <= b && b <= 'z' || 'A' <= b && b <= 'Z' || b == '_';
    }

    private boolean isDigit(byte b) { return '0' <= b && b <= '9'; }
}
