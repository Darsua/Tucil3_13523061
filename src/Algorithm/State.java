package Algorithm;

import Algorithm.Heuristic.*;
import Game.Board;
import Game.Piece.*;

import java.util.*;

public record State(Board board, List<Move> moves) {
    public static int nodes = 0;

    public static char primary;
    public static Heuristic heuristic = Heuristic.BLOCKING_DISTANCE;

    public State(Board board, List<Move> moves) {
        this.board = board;
        this.moves = moves;
        nodes++;
    }

    public static void setHeuristic(Heuristic heuristic) {
        State.heuristic = heuristic;
    }

    public int heuristic() {
        switch (heuristic) {
            case BLOCKING_PIECES -> {
                return BlockingPieces.calculate(this);
            }
            case EXIT_DISTANCE -> {
                return ExitDistance.calculate(this);
            }
            case BLOCKING_DISTANCE -> {
                return BlockingDistance.calculate(this);
            }
        }
        return 999;
    }
}