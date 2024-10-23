package edu.psu.ist.monkey.phase;

import edu.psu.ist.monkey.lang.Token;

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
            ch = input.getBytes()[readPosition];
        }
        position = readPosition;
        readPosition = readPosition + 1;
    }

    public Token nextToken() {
        var tok = switch (ch) {
            case '=' -> Token.Assign.AssignInst;
            case ';' -> Token.Semi.SemiInst;
            case '(' -> Token.LParen.LParenInst;
            case ')' -> Token.RParen.RParenInst;
            case ',' -> Token.Comma.CommaInst;
            case '+' -> Token.Plus.PlusInst;
            case '{' -> Token.LBrace.LBraceInst;
            case '}' -> Token.RBrace.RBraceInst;
            case 0 -> Token.Eof.EofInst;
            default -> {
                if (isLetter(ch)) {
                    var rawId = readIdentifier();
                    // returns a kw if rawId contains one
                    yield Token.fromText(rawId);
                } else {
                    yield Token.Illegal.IllegalInst;
                }
            }
        };
        readChar();
        return tok;
    }

    private String readIdentifier() {
        var a = this.position;
        while (isLetter(ch)) {
            readChar();
        }
        return input.substring(a, this.position);
    }

    private boolean isLetter(byte ch) {
        return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_';
    }
}
