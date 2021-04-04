package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void test_parsesUnaryNodesCorrectly() throws Exception {
        Parser p = new Parser(new Lexer("-6"));
        ASTNode expected = new UnaryNode(
                Token.MINUS,
                new ScalarNode(Token.number("6"))
        );
        assertEquals(expected, p.parse());
    }

    @Test
    public void test_parsesAssignmentNodesCorrectly() throws Exception {
        Parser p = new Parser(new Lexer("a = 3"));
        ASTNode expected = new AssignmentNode(
                Token.EQUALS,
                new VariableNode(Token.variable("a")),
                new ScalarNode(Token.number("3"))
        );
        assertEquals(expected, p.parse());
    }

    @Test
    public void test_parsesBinaryNodesCorrectly() throws Exception {
        Parser p = new Parser(new Lexer("2 * 6"));
        ASTNode expected = new BinaryNode(
                Token.MULTIPLY,
                new ScalarNode(Token.number("2")),
                new ScalarNode(Token.number("6"))
        );
        assertEquals(expected, p.parse());
    }

    @Test
    public void test_parsesParenthesesCorrectly() throws Exception {
        Parser p = new Parser(new Lexer("(1 + 3) * 10"));
        ASTNode expected = new BinaryNode(
                Token.MULTIPLY,
                new BinaryNode(
                        Token.PLUS,
                        new ScalarNode(Token.number("1")),
                        new ScalarNode(Token.number("3"))
                ),
                new ScalarNode(Token.number("10"))
        );
        assertEquals(expected, p.parse());
    }

    @Test
    public void test_throwsErrorIfParenthesesAreMismatched() {
        Parser p = new Parser(new Lexer("(1 + 3 * 10"));
        assertThrows(Exception.class, p::parse);
    }
}
