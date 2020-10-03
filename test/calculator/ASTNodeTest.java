package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ASTNodeTest {

    @Test
    public void printTest() {
        // Test with just a number
        ASTNode numberNode = new ScalarNode(Token.number("2"), "2");

        assertEquals("2", numberNode.toString());

        // Test unary node
        ASTNode minusNode = new UnaryNode(new Token(Token.Type.MINUS), numberNode);

        assertEquals("MINUS2", minusNode.toString());

        // Test binary node
        ASTNode numberNode1 = new ScalarNode(Token.number("3"), "3");
        ASTNode binaryNode = new BinaryNode(new Token(Token.Type.MULTIPLY), numberNode, numberNode1);

        assertEquals("2 MULTIPLY 3", binaryNode.toString());
    }
}