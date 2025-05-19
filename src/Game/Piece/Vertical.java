package Game.Piece;

import Game.Board;

import java.util.*;

public class Vertical extends Piece {

    public Vertical(char piece, Board board, int x, int y) {
        super(piece, board);
        this.points.add(new Point(x,y));
        if (board.exit.x == x())
            toExit = this.getFront().y < board.exit.y ? Direction.DOWN : Direction.UP;
    }

    public Vertical(Vertical other, Board newBoard) {
        super(other, newBoard);
    }

    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        Point front = getFront(), back = getBack();

        // Check if the piece can move up
        if (back.y > 0 && board.emptyAt(back.x, back.y - 1)) {
            moves.add(new Move(piece, Direction.UP));
        }
        // Check if the piece can move down
        if (front.y < board.y() - 1 && board.emptyAt(front.x, front.y + 1)) {
            moves.add(new Move(piece, Direction.DOWN));
        }

        return moves;
    }

    private int x() {
        return this.getFront().x;
    }

    public boolean atGoal() {
        return this.getFront().y == board.exit.y - 1 || this.getBack().y == board.exit.y + 1;
    }
}
