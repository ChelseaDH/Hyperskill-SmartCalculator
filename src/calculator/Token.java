package calculator;

public class Token {

    public static final Token END = new Token(Type.END);
    public static final Token PLUS = new Token(Type.PLUS);
    public static final Token MINUS = new Token(Type.MINUS);
    public static final Token MULTIPLY = new Token(Type.MULTIPLY);
    public static final Token DIVIDE = new Token(Type.DIVIDE);

    private final Type type;
    private String value;

    public Token(Type type) {
        this.type = type;
    }

    public static Token number(String value) {
        Token t = new Token(Type.NUMBER);
        t.value = value;
        return t;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    // isScalar returns true if the token is a number
    public boolean isScalar() {
        return this.type == Type.NUMBER;
    }

    // isUnary returns true if the token can be a unary expression
    public boolean isUnary() {
        switch (this.type) {
            case PLUS:
            case MINUS:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    public enum Type {
        END(0, false),
        NUMBER(1, true),
        PLUS(2, true),
        MINUS(2, true),
        MULTIPLY(3, true),
        DIVIDE(3, true);

        private final int precedence;
        private final boolean leftAssoc;

        Type(int precedence, boolean leftAssoc) {
            this.precedence = precedence;
            this.leftAssoc = leftAssoc;
        }

        public int getPrecedence() {
            return precedence;
        }

        public boolean isLeftAssoc() {
            return leftAssoc;
        }
    }
}

