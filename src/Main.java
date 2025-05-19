import Algorithm.*;
import Game.*;
import Game.Piece.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Check if incorrect arguments were passed
        String path;
        if (args.length != 1) {
            System.out.println("Usage: java Main <file>");
            path = "test/a.txt";
        } else
            path = args[0];

        try {
            Replay.clear();
            Board board = Parser.parse(path);

            long startTime = System.nanoTime();
            List<Move> moves = Greedy.solve(board, 'P');
            long endTime = System.nanoTime();

            double deltaSeconds = (endTime - startTime) / 1_000_000_000.0; // convert to seconds

            if (moves == null) {
                System.out.println("No solution found.");
            } else {
                System.out.println("Solution found!");
                System.out.println("Moves: " + moves.size());
                for (Move move : moves) {
                    System.out.println(move.piece() + " " + move.direction());
                }
            }
            System.out.println("\nMoves calculated: " + Algorithm.State.nodes);
            System.out.println("Elapsed time: " + deltaSeconds + " seconds");

            if (moves != null) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("\nPress Enter to replay moves...");
                scanner.nextLine();

                new Replay(board, moves, 'P');
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
