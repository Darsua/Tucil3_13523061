package Algorithm.Heuristic;

import Game.*;
import Game.Piece.*;
import Algorithm.*;

import java.util.*;

public class DistanceBlocked {
    public static int calculate(State state) {
        Piece primaryPiece = state.board().getPiece(State.primary);
        int distance = getDistance(state, primaryPiece);

        int blockingPieces = 0;
        Set<Character> counted = new HashSet<>();

        // Count blocking pieces in the way, penalizing immovable ones more
        switch (primaryPiece.toExit) {
            case UP -> {
                int x = primaryPiece.getBack().x;
                for (int y = state.board().exit.y + 1; y < primaryPiece.getBack().y; y++) {
                    char cell = state.board().getCell(x, y);
                    if (cell != '.' && cell != State.primary && counted.add(cell)) {
                        blockingPieces += getPenalty(state.board(), cell);
                    }
                }
            }
            case DOWN -> {
                int x = primaryPiece.getFront().x;
                for (int y = primaryPiece.getFront().y + 1; y < state.board().exit.y; y++) {
                    char cell = state.board().getCell(x, y);
                    if (cell != '.' && cell != State.primary && counted.add(cell)) {
                        blockingPieces += getPenalty(state.board(), cell);
                    }
                }
            }
            case LEFT -> {
                int y = primaryPiece.getBack().y;
                for (int x = state.board().exit.x + 1; x < primaryPiece.getBack().x; x++) {
                    char cell = state.board().getCell(x, y);
                    if (cell != '.' && cell != State.primary && counted.add(cell)) {
                        blockingPieces += getPenalty(state.board(), cell);
                    }
                }
            }
            case RIGHT -> {
                int y = primaryPiece.getFront().y;
                for (int x = primaryPiece.getFront().x + 1; x < state.board().exit.x; x++) {
                    char cell = state.board().getCell(x, y);
                    if (cell != '.' && cell != State.primary && counted.add(cell)) {
                        blockingPieces += getPenalty(state.board(), cell);
                    }
                }
            }
        }

        return distance * 3 + blockingPieces;
    }

    private static int getDistance(State state, Piece primaryPiece) {
        int distance = 0;

        // Calculate straight-line distance from primary piece to exit
        switch (primaryPiece.toExit) {
            case UP -> distance = Math.abs(primaryPiece.getBack().y - state.board().exit.y);
            case DOWN -> distance = Math.abs(state.board().exit.y - primaryPiece.getFront().y);
            case LEFT -> distance = Math.abs(primaryPiece.getBack().x - state.board().exit.x);
            case RIGHT -> distance = Math.abs(state.board().exit.x - primaryPiece.getFront().x);
        }
        return distance;
    }

    private static int getPenalty(Board board, char pieceChar) {
        Piece blocking = board.getPiece(pieceChar);
        if (blocking == null) return 0; // No piece, no penalty

        if (blocking.getMoves().isEmpty()) return 3; // No possible moves (immovable)
        return 1; // Movable piece
    }
}
