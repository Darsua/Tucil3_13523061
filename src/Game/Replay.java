package Game;

import Game.Piece.*;

import java.util.*;

public class Replay {
    private final Board board;
    private final Set<Point> lastMove;
    private final char primary;

    public static void clear() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void print() {
        for (int i = 0; i < board.y() + 2; i++) {
            for (int j = 0; j < board.x() + 2; j++) {
                if (i == board.exit.y + 1 && j == board.exit.x + 1) System.out.print(' ');
                else if (i == 0 || i == board.y() + 1 || j == 0 || j == board.x() + 1) System.out.print('█');
                else {
                    char c = board.cells[i - 1][j - 1];
                    if (lastMove.contains(new Point(j - 1, i - 1))) {
                        if (c == primary) System.out.print("\u001B[31m\u001B[100m" + c + "\u001B[0m");
                        else System.out.print("\u001B[100m" + c +"\u001B[0m");
                    }
                    else if (c == primary) System.out.print("\u001B[31m" + c + "\u001B[0m");
                    else System.out.print(c);
                }
            }
            System.out.println();
        }
    }

    public Replay(Board board, List<Move> moves, char primary) {
        this.board = board;
        this.lastMove = new HashSet<>();
        this.primary = primary;

        clear();
        print();
        System.out.println("INITIAL STATE");
        System.out.print("\nPress Enter to continue...");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        for (Move move : moves) {
            clear();
            lastMove.clear();

            for (Point p : board.getPiece(move.piece()).points)
                lastMove.add(new Point(p.x, p.y));
            board.apply(move);
            for (Point p : board.getPiece(move.piece()).points)
                lastMove.add(new Point(p.x, p.y));

            print();
            System.out.println(move.piece() + " " + move.direction());

            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }

        clear();
        print();
        System.out.println("Board completed in " + moves.size() + " moves.\n");
    }
}
