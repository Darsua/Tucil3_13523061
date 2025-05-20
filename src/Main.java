import Algorithm.*;
import Algorithm.Heuristic.Heuristic;
import Game.*;
import Game.Piece.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        if (args.length < 2 || args.length > 3) {
            String command;
            if (Objects.requireNonNull(Main.class.getResource("Main.class")).toString().startsWith("jar")) {
                command = "java -jar Rush.jar <file> <algorithm> [heuristic]";
            } else {
                command = "java Main <file> <algorithm> [heuristic]";
            }
            System.out.println("\nUsage: " + command + "\n");
            System.out.println("Algorithm: GBFS, UCS, A_STAR");
            System.out.println("Heuristic: BLOCKING_PIECES, EXIT_DISTANCE, BLOCKING_DISTANCE (default)\n");
            System.exit(0);
        }

        if (!args[1].equals("GBFS") && !args[1].equals("UCS") && !args[1].equals("A_STAR")) {
            System.out.println("\nInvalid algorithm. Use GBFS, UCS, or A_STAR.\n");
            System.exit(0);
        }

        if (args.length == 3) {
            if (!args[2].equals("BLOCKING_PIECES") && !args[2].equals("EXIT_DISTANCE")
                    && !args[2].equals("BLOCKING_DISTANCE")) {
                System.out.println("\nInvalid heuristic. Use BLOCKING_PIECES, EXIT_DISTANCE, or BLOCKING_DISTANCE.\n");
                System.exit(0);
            }
            State.setHeuristic(Heuristic.valueOf(args[2]));
        }

        try {
            Board board = Parser.parse(args[0]);
            System.out.println(board);

            long startTime = System.nanoTime();
            List<Move> moves = Solver.solve(board, 'P', Algorithm.valueOf(args[1]));
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
            System.out.println("\nMoves calculated: " + State.nodes);
            System.out.println("Elapsed time: " + deltaSeconds + " seconds");

            if (moves != null) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("\nWatch replay? (y?) ");

                boolean watch = scanner.nextLine().equalsIgnoreCase("y");

                new Replay(board, moves, 'P', watch);

                String save = args[0].replace(".txt", ".replay.txt");
                Parser.write(save, Replay.fullReplay.toString());
                System.out.println("Replay saved to " + save + "\n");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
