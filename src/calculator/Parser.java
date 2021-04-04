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

        if (current.isAssignment()) {
            return new AssignmentNode(current);
        }

        ASTNode left = this.parseTerm();
        if (next.isRightParenthesis()) {
            current = next;
            next = lexer.next();
            return left;
        }

        if (current.isUnary()) {
            left = new UnaryNode(current, this.expr(Integer.MAX_VALUE));
        }

        // Left binding power
        while (rbp < next.getType().getPrecedence()) {
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
            return new ScalarNode(current, current.getValue());
        } else if (current.isVariable()) {
            return new VariableNode(current);
        }

        throw new Exception("Invalid Expression");
    }

    private ASTNode parseParenthesizedExpression() throws Exception {
        ASTNode node = this.expr(next.getType().getPrecedence());
        if (!current.isRightParenthesis() && next.isEnd()) {
            throw new Exception("Mismatched parenthesis");
        }
        return node;
    }
}
