package calculator;

public class Parser {
    Lexer lexer;
    Token current;
    Token next;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public ASTNode parse() throws Exception {
        next = this.lexer.next();
        return this.expr(0);
    }

    private ASTNode expr(int rbp) throws Exception {
        current = next;
        next = lexer.next();

        ASTNode left;

        if (current.isUnary()) {
            left = new UnaryNode(current, this.expr(Integer.MAX_VALUE));
        } else {
            left = this.parseTerm();
        }

        if (next.getType() == Token.Type.EQUALS) {
            current = next;
            next = lexer.next();

            left = new AssignmentNode(current, left, expr(0));
        }

        // Left binding power
        while (rbp < next.getType().getPrecedence() && !next.isRightParenthesis()) {
            current = next;
            next = lexer.next();

            left = new BinaryNode(current, left, this.expr(current.getType().getPrecedence()));
        }

        return left;
    }

    private ASTNode parseTerm() throws Exception {
        if (current.isLeftParenthesis()) {
            return parseParenthesizedExpression();
        } else if (current.isScalar()) {
            return new ScalarNode(current);
        } else if (current.isVariable()) {
            return new VariableNode(current);
        }

        throw new Exception("Invalid Expression");
    }

    private ASTNode parseParenthesizedExpression() throws Exception {
        ASTNode node = this.expr(next.getType().getPrecedence());
        if (!next.isRightParenthesis()) {
            throw new Exception("Mismatched parenthesis");
        }

        current = next;
        next = lexer.next();
        return node;
    }
}
