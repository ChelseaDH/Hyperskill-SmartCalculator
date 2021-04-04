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
    ASTNode variable;
    ASTNode expr;

    public AssignmentNode(Token token, ASTNode variable, ASTNode expr) {
        super(token);
        this.variable = variable;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", variable.toString(), token.getType().toString(), expr.toString());
    }

    @Override
    public Optional<Double> evaluate(Map<String, Double> variables) throws Exception {
        if (!(this.variable instanceof VariableNode)) {
            throw new Exception("Invalid identifier");
        }

        String identifier = this.variable.toString();
        Optional<Double> value = this.expr.evaluate(variables);

        if (value.isEmpty()) {
            throw new Exception("Invalid assignment");
        }

        variables.put(identifier, value.get());
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
