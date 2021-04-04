package calculator;

import org.assertj.swing.util.Maps;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ASTNodeTest {
    ScalarNode scalarNode = new ScalarNode(Token.number("2"));
    VariableNode variableNode = new VariableNode(Token.variable("a"));

    @Test
    public void test_ScalarNode_evaluatesCorrectly() {
        assertEquals(Optional.of(2.0), scalarNode.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_UnaryNode_throwsExceptionIfTokenIsInvalid() {
        UnaryNode node = new UnaryNode(Token.MULTIPLY, scalarNode);
        assertThrows(Exception.class, () -> node.evaluate(new HashMap<>()), "Invalid token type");
    }

    @Test
    public void test_UnaryNode_evaluatesCorrectlyWithNegative() throws Exception {
        UnaryNode node = new UnaryNode(Token.MINUS, scalarNode);
        assertEquals(Optional.of(-2.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_UnaryNode_evaluatesCorrectlyWithPositive() throws Exception {
        UnaryNode node = new UnaryNode(Token.PLUS, scalarNode);
        assertEquals(Optional.of(2.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_BinaryNode_throwsExceptionIfTokenIsInvalid() {
        BinaryNode node = new BinaryNode(Token.EQUALS, scalarNode, scalarNode);
        assertThrows(Exception.class, () -> node.evaluate(new HashMap<>()), "Invalid token type");
    }

    @Test
    public void test_BinaryNode_evaluatesCorrectlyWithAddition() throws Exception {
        BinaryNode node = new BinaryNode(Token.PLUS, scalarNode, scalarNode);
        assertEquals(Optional.of(4.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_BinaryNode_evaluatesCorrectlyWithSubtraction() throws Exception {
        BinaryNode node = new BinaryNode(Token.MINUS, scalarNode, scalarNode);
        assertEquals(Optional.of(0.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_BinaryNode_evaluatesCorrectlyWithMultiplication() throws Exception {
        BinaryNode node = new BinaryNode(Token.MULTIPLY, scalarNode, scalarNode);
        assertEquals(Optional.of(4.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_BinaryNode_evaluatesCorrectlyWithDivision() throws Exception {
        BinaryNode node = new BinaryNode(Token.DIVIDE, scalarNode, scalarNode);
        assertEquals(Optional.of(1.0), node.evaluate(Maps.newHashMap()));
    }

    @Test
    public void test_VariableNode_evaluatesCorrectlyIfVariableExists() throws Exception {
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 6.0);

        assertEquals(Optional.of(6.0), variableNode.evaluate(variables));
    }

    @Test
    public void test_VariableNode_throwsExceptionIfVariableDoesNotExist() {
        Map<String, Double> variables = new HashMap<>();

        assertThrows(Exception.class, () -> variableNode.evaluate(variables), "Unknown variable");
    }

    @Test
    public void test_AssignmentNode_correctlyAddsNewVariable() throws Exception {
        Map<String, Double> variables = new HashMap<>();

        AssignmentNode node = new AssignmentNode(Token.EQUALS, variableNode, scalarNode);

        assertEquals(Optional.empty(), node.evaluate(variables));
        assertTrue(variables.containsKey("a"));
        assertEquals(variables.get("a"), 2);
    }

    @Test
    public void test_AssignmentNode_correctlyAssignsNewValueToExistingVariable() throws Exception {
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 6.0);

        AssignmentNode node = new AssignmentNode(Token.EQUALS, variableNode, scalarNode);

        assertEquals(Optional.empty(), node.evaluate(variables));
        assertEquals(variables.get("a"), 2);
    }
}
