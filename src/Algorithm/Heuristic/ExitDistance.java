package Algorithm.Heuristic;

import Game.Piece.*;
import Algorithm.*;

public class ExitDistance {
    public static int calculate(State state) {
        Piece primaryPiece = state.board().getPiece(State.primary);

        return getDistance(state, primaryPiece);
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
}
