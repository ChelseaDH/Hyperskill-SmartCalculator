package calculator;

import java.util.Objects;

public class Token {

    public static final Token END = new Token(Type.END);
    public static final Token EQUALS = new Token(Type.EQUALS);
    public static final Token PLUS = new Token(Type.PLUS);
    public static final Token MINUS = new Token(Type.MINUS);
    public static final Token MULTIPLY = new Token(Type.MULTIPLY);
    public static final Token DIVIDE = new Token(Type.DIVIDE);
    public static final Token LEFT_PARENTHESES = new Token(Type.LEFT_PARENTHESES);
    public static final Token RIGHT_PARENTHESES = new Token(Type.RIGHT_PARENTHESES);

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

    public static Token variable(String value) {
        Token t = new Token(Type.VARIABLE);
        t.value = value;
        return t;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isScalar() {
        return this.type == Type.NUMBER;
    }

    public boolean isUnary() {
        switch (this.type) {
            case PLUS:
            case MINUS:
                return true;
            default:
                return false;
        }
    }

    public boolean isVariable() { return this.type == Type.VARIABLE; }
    public boolean isLeftParenthesis() {return this.type == Type.LEFT_PARENTHESES; }
    public boolean isRightParenthesis() {return this.type == Type.RIGHT_PARENTHESES; }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    public enum Type {
        END(0),
        EQUALS(0),
        NUMBER(1),
        VARIABLE(1),
        PLUS(2),
        MINUS(2),
        MULTIPLY(3),
        DIVIDE(3),
        LEFT_PARENTHESES(4),
        RIGHT_PARENTHESES(4);

        private final int precedence;

        Type(int precedence) {
            this.precedence = precedence;
        }

        public int getPrecedence() {
            return precedence;
        }
    }
}
