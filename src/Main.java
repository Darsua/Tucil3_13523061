import Game.*;

public class Main {

    public static void main(String[] args) {
        // Check if incorrect arguments were passed
        if (args.length != 1) {
            System.out.println("Usage: java Main <file>");
            // System.exit(0);
        }

        try {
            Board board = Parser.parse("test/a.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
