package Game.Piece;

import Game.Board;

import java.util.*;

public class Horizontal extends Piece {

    public Horizontal(char piece, Board board, int x, int y) {
        super(piece, board);
        this.points.add(new Point(x,y));
        if (board.exit.y == y())
            toExit = this.getFront().x < board.exit.x ? Direction.RIGHT : Direction.LEFT;
    }

    public Horizontal(Horizontal other, Board newBoard) {
        super(other, newBoard);
    }

    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        Point front = getFront(), back = getBack();

        // Check if the piece can move left
        if (back.x > 0 && board.emptyAt(back.x - 1, back.y)) {
            moves.add(new Move(piece, Direction.LEFT));
        }
        // Check if the piece can move right
        if (front.x < board.x() - 1 && board.emptyAt(front.x + 1, front.y)) {
            moves.add(new Move(piece, Direction.RIGHT));
        }

        return moves;
    }

    private int y() {
        return this.getFront().y;
    }

    public boolean atGoal() {
        return this.getFront().x == board.exit.x - 1 || this.getBack().x == board.exit.x + 1;
    }
}
