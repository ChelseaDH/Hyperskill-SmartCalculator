package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerTest {

    @Test
    public void simpleLexerTest() throws LexerException {
        Lexer testLexer = new Lexer("2 + 2");

        assertEquals(Token.number("2"), testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertEquals(Token.number("2"), testLexer.next());
        assertEquals(Token.END, testLexer.next());

        testLexer = new Lexer("56 + 89 - 273");

        assertEquals(Token.number("56"), testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertEquals(Token.number("89"), testLexer.next());
        assertEquals(Token.MINUS, testLexer.next());
        assertEquals(Token.number("273"), testLexer.next());
        assertEquals(Token.END, testLexer.next());

        testLexer = new Lexer("5 +++ 6");

        assertEquals(Token.number("5"), testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertEquals(Token.number("6"), testLexer.next());
        assertEquals(Token.END, testLexer.next());
    }

    @Test
    public void invalidInput() throws LexerException {
        assertThrows(LexerException.class, () -> new Lexer("____").next());

        Lexer testLexer = new Lexer("2 + __");

        assertEquals(Token.number("2"), testLexer.next());
        assertEquals(Token.PLUS, testLexer.next());
        assertThrows(LexerException.class, testLexer::next);
    }
}
