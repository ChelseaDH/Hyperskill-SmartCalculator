package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void plusAndMinusTest() throws Exception {
        Parser p = new Parser(new Lexer("2 + 2"));
        assertEquals(4.0, p.parse());

        p = new Parser(new Lexer("5 + 6 - 10"));
        assertEquals(1.0, p.parse());

        p = new Parser(new Lexer("1 + 2 - 3 + 4 + 5.01 - 6"));
        assertEquals(3.01, p.parse());
    }

    @Test
    public void multiplyAndDivideTest() throws Exception {
        Parser p = new Parser(new Lexer("2 * 6"));
        assertEquals(12.0, p.parse());

        p = new Parser(new Lexer("5 + 6 * 10"));
        assertEquals(65.0, p.parse());

        p = new Parser(new Lexer("5 + 6 * 10 + 80 / 10"));
        assertEquals(73.0, p.parse());
    }
}