package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParserTest {
    public Map<String, Double> variables = new HashMap<>();

    @Test
    public void plusAndMinusTest() throws Exception {
        Parser p = new Parser(new Lexer("2 + 2"));
        assertEquals(Optional.of(4.0), p.parse().evaluate(variables));

        p = new Parser(new Lexer("5 + 6 - 10"));
        assertEquals(Optional.of(1.0), p.parse().evaluate(variables));

        p = new Parser(new Lexer("1 + 2 - 3 + 4 + 5.01 - 6"));
        assertEquals(Optional.of(3.01), p.parse().evaluate(variables));
    }

    @Test
    public void multiplyAndDivideTest() throws Exception {
        Parser p = new Parser(new Lexer("2 * 6"));
        assertEquals(Optional.of(12.0), p.parse().evaluate(variables));

        p = new Parser(new Lexer("5 + 6 * 10"));
        assertEquals(Optional.of(65.0), p.parse().evaluate(variables));

        p = new Parser(new Lexer("5 + 6 * 10 + 80 / 10"));
        assertEquals(Optional.of(73.0), p.parse().evaluate(variables));
    }
}
