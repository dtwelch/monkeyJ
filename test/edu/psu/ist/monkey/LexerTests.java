package edu.psu.ist.monkey;

import edu.psu.ist.monkey.lang.Token;
import edu.psu.ist.monkey.phase.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class LexerTests {

    @Test public void testNextToken01() {
        var input = "=+(){},;";

        Token[] expectedTokens = {
                Token.Assign.AssignInst, //
                Token.Plus.PlusInst, //
                Token.LParen.LParenInst, //
                Token.RParen.RParenInst, //
                Token.LBrace.LBraceInst, //
                Token.RBrace.RBraceInst, //
                Token.Comma.CommaInst, //
                Token.Semi.SemiInst, //
                Token.Eof.EofInst //
        };
        var l = new Lexer(input);
        for (Token expectedToken : expectedTokens) {
            var actualToken = l.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }
    }

    @Test public void testSimpleProg01() {
        var input = """
                let five = 5;
                """.trim();

        Token[] expectedTokens = {
                Token.Let.LetInst,
                new Token.Ident("five"),
                Token.Assign.AssignInst,
                new Token.Int(5),
                Token.Semi.SemiInst,
                Token.Eof.EofInst
        };
        var l = new Lexer(input);
        for (Token expectedToken : expectedTokens) {
            var actualToken = l.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }
    }
}
