package Algorithm;

import Algorithm.Heuristic.*;
import Game.Board;
import Game.Piece.*;

import java.util.*;

public record State(Board board, List<Move> moves) {
    public static int nodes = 0;

    public static char primary;
    public static Heuristic heuristic = Heuristic.DISTANCE_BLOCKED;

    public State(Board board, List<Move> moves) {
        this.board = board;
        this.moves = moves;
        nodes++;
    }

    public int heuristic() {
        switch (heuristic) {
            case DISTANCE_BLOCKED -> {
                return DistanceBlocked.calculate(this);
            }
        }
        return 999;
    }
}