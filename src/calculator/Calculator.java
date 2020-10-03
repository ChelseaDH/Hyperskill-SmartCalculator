package calculator;

import java.util.Scanner;

public class Calculator {

    public void run(Scanner s) {
        String inputLine;

        while (true) {
            // Get user input
            inputLine = s.nextLine().strip();

            // Look for a command
            if (inputLine.startsWith("/")) {
                switch (inputLine) {
                    case "/exit":
                        System.out.println("Bye!");
                        System.exit(0);
                        break;
                    case "/help":
                        System.out.println("It's a calculator yo");
                        break;
                    default:
                        System.out.println("Unknown command");
                }
                continue;
            }

            // Ignore empty input
            if (inputLine.isEmpty()) {
                continue;
            }

            try {
                // Create Lexer and parser
                Parser parser = new Parser(new Lexer(inputLine));

                // Print parser result
                System.out.println(parser.parse().evaluate());
            } catch (Exception e) {
                System.out.println("Invalid expression");
            }
        }
    }
}
