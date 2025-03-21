package edu.psu.ist.monkey;

import edu.psu.ist.monkey.lang.Token;
import edu.psu.ist.monkey.phase.Lexer;
import io.vavr.collection.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class LexerTests {

    @Test public void testNextToken01() {
        var input = "=+(){},;";

        Token[] expectedTokens = {Token.Assign.AssignInst, //
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

        Token[] expectedTokens = {Token.Let.LetInst, //
                new Token.Ident("five"), //
                Token.Assign.AssignInst, //
                new Token.Num("5"), //
                Token.Semi.SemiInst,  //
                Token.Eof.EofInst //
        };
        var l = new Lexer(input);

        for (Token expectedToken : expectedTokens) {
            var actualToken = l.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }
    }

    @Test public void testSimpleProg02() {
        var input = """
                let five = 5;
                let ten = 10;
                
                let add = fn(x,y) {
                    x + y;
                };
                
                let result = add(five, ten);
                """.trim();

        var expectedTokens = simpleProgExpectedTokens().append(Token.Eof.EofInst);
        var l = new Lexer(input);

        for (Token expectedToken : expectedTokens) {
            var actualToken = l.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }
    }

    @Test public void testSimpleProg03() {
        var input = """
                %s
                !-/*5;
                5 < 10 > 5;
                """.formatted(sampleProg01).trim();
        var expectedTokens = simpleProgExpectedTokens().appendAll(Vector.of( //
                Token.Bang.BangInst, //
                Token.Minus.MinusInst, //
                Token.Slash.SlashInst, //
                Token.Asterisk.AsteriskInst, //
                new Token.Num("5"), //
                Token.Semi.SemiInst, //
                new Token.Num("5"), //
                Token.Lt.LtInst, //
                new Token.Num("10"), //
                Token.Gt.GtInst, //
                new Token.Num("5"), //
                Token.Semi.SemiInst, //
                Token.Eof.EofInst));
        var l = new Lexer(input);
        for (Token expectedToken : expectedTokens) {
            var actualToken = l.nextToken();
            Assertions.assertEquals(expectedToken, actualToken);
        }
    }

    private static final String sampleProg01 = """
                let five = 5;
                let ten = 10;
            
                let add = fn(x, y) {
                    x + y;
                };
                let result = add(five, ten);
            """.trim();

    /**
     * Returns the expected tokens that make up the following small
     * monkey program:
     * <pre><code>
     * let five = 5;
     * let ten = 10;
     * let add = fn(x, y) {
     *      x + y
     * };
     * let result = add(five, ten);
     * </code></pre>
     * NOTE: callers must manually add the {@link Token.Eof} to the end.
     */
    private Vector<Token> simpleProgExpectedTokens() {
        return Vector.of( //
                // let five = 5
                Token.Let.LetInst, //
                new Token.Ident("five"), //
                Token.Assign.AssignInst, //
                new Token.Num("5"), //
                Token.Semi.SemiInst, //
                //
                // let ten = 10
                Token.Let.LetInst, //
                new Token.Ident("ten"), //
                Token.Assign.AssignInst, //
                new Token.Num("10"), //
                Token.Semi.SemiInst, //
                //
                // let add = fn(x, y)
                Token.Let.LetInst, //
                new Token.Ident("add"), //
                Token.Assign.AssignInst, //
                Token.Fun.FunInst, //
                Token.LParen.LParenInst, //
                new Token.Ident("x"), //
                Token.Comma.CommaInst,//
                new Token.Ident("y"), //
                Token.RParen.RParenInst, //
                //
                // { x + y; };
                Token.LBrace.LBraceInst, //
                new Token.Ident("x"), //
                Token.Plus.PlusInst, //
                new Token.Ident("y"), //
                Token.Semi.SemiInst, //
                Token.RBrace.RBraceInst, //
                Token.Semi.SemiInst, //
                //
                // let result = add(five, ten);
                Token.Let.LetInst, //
                new Token.Ident("result"), //
                Token.Assign.AssignInst, //
                new Token.Ident("add"), //
                Token.LParen.LParenInst, //
                new Token.Ident("five"), //
                Token.Comma.CommaInst, //
                new Token.Ident("ten"), //
                Token.RParen.RParenInst, //
                Token.Semi.SemiInst);
    }
}
