package calculator;

public class LexerException extends Exception {
    public LexerException(String message, long col) {
        super(message + " at column " + col);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
