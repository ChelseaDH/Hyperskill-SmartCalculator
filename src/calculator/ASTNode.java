package calculator;

import java.util.Map;
import java.util.Optional;

public abstract class ASTNode {
    Token token;

    public ASTNode(Token token) {
        this.token = token;
    }

    public abstract Optional<Double> evaluate(Map<String, Double> variables) throws Exception;
}

class ScalarNode extends ASTNode {
    String value;

    public ScalarNode(Token token, String value) {
        super(token);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) {
        return Optional.of(Double.parseDouble(value));
    }
}

class UnaryNode extends ASTNode {
    ASTNode expr;

    public UnaryNode(Token token, ASTNode expr) {
        super(token);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return String.format("%s%s", token.getType().toString(), expr.toString());
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) throws Exception {
        Optional<Double> value = expr.evaluate(variables);

        if (value.isEmpty()) {
            throw new Exception("Invalid Expression");
        }

        switch (token.getType()) {
            case PLUS:
                return value;
            case MINUS:
                return Optional.of(-value.get());
            default:
                throw new Exception("Invalid Expression");
        }
    }
}

class BinaryNode extends ASTNode {
    ASTNode expr1;
    ASTNode expr2;

    public BinaryNode(Token token, ASTNode expr1, ASTNode expr2) {
        super(token);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", expr1.toString(), token.getType().toString(), expr2.toString());
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) throws Exception {
        Optional<Double> value1 = expr1.evaluate(variables);
        Optional<Double> value2 = expr2.evaluate(variables);

        if (value1.isEmpty() || value2.isEmpty()) {
            throw new Exception("Invalid Expression");
        }

        switch (this.token.getType()) {
            case PLUS:
                return Optional.of(value1.get() + value2.get());
            case MINUS:
                return Optional.of(value1.get() - value2.get());
            case MULTIPLY:
                return Optional.of(value1.get() * value2.get());
            case DIVIDE:
                return Optional.of(value1.get() / value2.get());
            default:
                throw new Exception("Invalid Expression");
        }
    }
}

class AssignmentNode extends ASTNode {
    public AssignmentNode (Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return token.getValue();
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) throws Exception {
        String[] parts = token.getValue().split("=");
        String identifier;
        String assignee;
        double value;

        if (parts.length != 2) {
            throw new Exception("Invalid assignment");
        }

        identifier = parts[0].strip();
        assignee = parts[1].strip();

        if (!identifier.matches("^[a-zA-Z]+")) {
            throw new Exception("Invalid identifier");
        }

        if (assignee.matches("^\\d+([.,]\\d+)?")) {
            value = Double.parseDouble(assignee);
        } else if (assignee.matches("^[a-zA-Z]+")) {
            Double variable = variables.get(assignee);

            if (variable == null) {
                throw new Exception("Unknown variable");
            } else {
                value = variable;
            }
        } else {
            throw new Exception("Invalid assignment");
        }

        variables.put(identifier, value);

        return Optional.empty();
    }
}

class VariableNode extends ASTNode {
    public VariableNode(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return token.getValue();
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) throws Exception {
        Double value = variables.get(token.getValue());

        if (value == null) {
            throw new Exception("Unknown variable");
        }

        return Optional.of(value);
    }
}
