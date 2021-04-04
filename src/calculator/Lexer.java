package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static Pattern numberPattern = Pattern.compile("^\\d+([.,]\\d+)?");
    public static Pattern variablePattern = Pattern.compile("^[a-zA-Z]+");

    private final String input;
    private int inputIndex;

    public Lexer(String input) {
        this.input = input;
        this.inputIndex = 0;
    }

    public Token next() throws LexerException {
        Token token = null;
        Matcher matcher;

        // Look for end of input
        if (inputIndex >= input.length()) {
            return Token.END;
        }

        // Create substring to be evaluated
        String temp = input.substring(inputIndex);
        // Strip leading whitespace
        String start = temp.stripLeading();
        // Calculate current index in input
        inputIndex += (temp.length() - start.length());

        // Regex match on the start of the input
        // If matched, add token to the list
        // Look for an operator
        switch (start.charAt(0)) {
            case '=':
                token = Token.EQUALS;
                inputIndex++;
                break;
            case '+':
                token = Token.PLUS;
                inputIndex++;
                break;
            case '-':
                token = Token.MINUS;
                inputIndex++;
                break;
            case '*':
                token = Token.MULTIPLY;
                inputIndex++;
                break;
            case '/':
                token = Token.DIVIDE;
                inputIndex++;
                break;
            case '(':
                token = Token.LEFT_PARENTHESES;
                inputIndex++;
                break;
            case ')':
                token = Token.RIGHT_PARENTHESES;
                inputIndex++;
                break;
        }

        // Look for a number
        if (token == null) {
            matcher = numberPattern.matcher(start);
            if (matcher.find()) {
                String group = matcher.group();
                token = Token.number(group);
                inputIndex += group.length();
            }
        }

        // Look for a variable
        if (token == null) {
            matcher = variablePattern.matcher(start);
            if (matcher.find()) {
                String group = matcher.group();
                token = Token.variable(group);
                inputIndex += group.length();
            }
        }

        // Else, throw error
        if (token == null) {
            throw new LexerException("Invalid expression", inputIndex + 1);
        }

        return token;
    }
}
