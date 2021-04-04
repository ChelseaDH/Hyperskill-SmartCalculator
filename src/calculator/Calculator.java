package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Calculator {
    public Map<String, Double> variables = new HashMap<>();

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
                Optional<Double> result = parser.parse().evaluate(variables);
                result.ifPresent(System.out::println);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
