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
            inputLine = s.nextLine().strip();

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

            if (inputLine.isEmpty()) {
                continue;
            }

            try {
                Parser parser = new Parser(new Lexer(inputLine));

                Optional<Double> result = parser.parse().evaluate(variables);
                result.ifPresent(System.out::println);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
