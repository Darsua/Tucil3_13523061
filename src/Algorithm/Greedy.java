package Algorithm;

import Game.*;
import Game.Piece.*;

import java.util.*;

public class Greedy {
    public static List<Move> solve(Board board, char primary) {
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::heuristic));
        Set<Board> visited = new HashSet<>();

        if (board.getPiece(primary).toExit == Direction.NULL) {
            System.out.println("Direction of primary to exit is " + board.getPiece(primary).toExit);
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

                    List<Move> newMoves = new ArrayList<>(current.moves());
                    newMoves.add(move);

                    queue.add(new State(newBoard, newMoves));
                }
            }
        }
        return null;
    }
}