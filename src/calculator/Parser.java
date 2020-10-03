package calculator;

public class Parser {
    Lexer lexer;
    Token next;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    // Parse tokens into an AST
    public ASTNode parse() throws Exception {
        next = this.lexer.next();
        return this.expr(0);
    }

    // Create the AST
    private ASTNode expr(int rbp) throws LexerException {
        Token current = next;
        next = lexer.next();

        // Left node
        ASTNode left = null;

        // Create scalar node
        if (current.isScalar()) {
            left = new ScalarNode(current, current.getValue());
        }

        // Create Unary node
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
}

