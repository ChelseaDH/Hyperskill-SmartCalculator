package calculator;

public abstract class ASTNode {
    Token token;

    public ASTNode(Token token) {
        this.token = token;
    }

    public abstract double evaluate() throws Exception;
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
    public double evaluate() {
        return Double.parseDouble(value);
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
    public double evaluate() throws Exception {
        switch (token.getType()) {
            case PLUS:
                return this.expr.evaluate();
            case MINUS:
                return -this.expr.evaluate();
        }
        throw new Exception("Invalid Expression");
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
    public double evaluate() throws Exception {
        switch (this.token.getType()) {
            case PLUS:
                return expr1.evaluate() + expr2.evaluate();
            case MINUS:
                return expr1.evaluate() - expr2.evaluate();
            case MULTIPLY:
                return expr1.evaluate() * expr2.evaluate();
            case DIVIDE:
                return expr1.evaluate() / expr2.evaluate();
        }
        throw new Exception("Invalid Expression");
    }
}