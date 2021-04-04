package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {
    @Test
    public void test_throwsExceptionIfInputIsInvalid() {
        assertThrows(LexerException.class, () -> new Lexer("____").next());
    }

    @Test
    public void test_evaluatesSymbolsCorrectly() throws LexerException {
        Lexer lexer = new Lexer("= + - * / ( )");
        assertEquals(Token.EQUALS, lexer.next());
        assertEquals(Token.PLUS, lexer.next());
        assertEquals(Token.MINUS, lexer.next());
        assertEquals(Token.MULTIPLY, lexer.next());
        assertEquals(Token.DIVIDE, lexer.next());
        assertEquals(Token.LEFT_PARENTHESES, lexer.next());
        assertEquals(Token.RIGHT_PARENTHESES, lexer.next());
    }

    @Test
    public void test_evaluatesNumbersCorrectly() throws LexerException {
        Lexer lexer = new Lexer("2 69.5 101");
        assertEquals(Token.number("2"), lexer.next());
        assertEquals(Token.number("69.5"), lexer.next());
        assertEquals(Token.number("101"), lexer.next());
    }

    @Test
    public void test_evaluatesVariablesCorrectly() throws LexerException {
        Lexer lexer = new Lexer("a count");
        assertEquals(Token.variable("a"), lexer.next());
        assertEquals(Token.variable("count"), lexer.next());
    }
}
