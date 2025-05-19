package Algorithm;

import Game.Board;
import Game.Piece.Direction;
import Game.Piece.Move;
import Game.Piece.Piece;

import java.util.*;

public class Solver {
    public static List<Move> solve(Board board, char primary, Algorithm algorithm) {
        Queue<State> queue;

        switch(algorithm) {
            case GBFS -> queue = new PriorityQueue<>(Comparator.comparingInt(s-> s.heuristic()));
            case UCS -> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.moves().size()));
            case A_STAR -> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.moves().size() + s.heuristic()));
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        Set<Board> visited = new HashSet<>();

        if (board.getPiece(primary).toExit == Direction.NULL) {
            System.out.println("Primary piece is not aligned with the exit.");
            return null;
        }

        State.primary = primary;
        queue.add(new State(board, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (visited.contains(current.board())) continue;
            visited.add(current.board());

            if (current.board().getPiece(primary).atGoal()) {
                return current.moves();
            }

            for (Piece piece : current.board().pieces.values()) {
                for (Move move : piece.getMoves()) {
                    Board newBoard = new Board(current.board());
                    newBoard.apply(move);

                    if (visited.contains(newBoard)) continue;

                    List<Move> newMoves = new ArrayList<>(current.moves());
                    newMoves.add(move);

                    queue.add(new State(newBoard, newMoves));
                }
            }
        }
        return null;
    }
}
