package Game.Piece;

import Game.Board;

import java.util.*;

public abstract class Piece {
    protected final char piece;
    protected final Board board;
    public Direction toExit = Direction.NULL;
    public final Vector<Point> points = new Vector<>();

    public Piece(char piece, Board board) {
        this.piece = piece;
        this.board = board;
    }

    public Piece(Piece other, Board newBoard) {
        this.piece = other.piece;
        this.board = newBoard;
        this.toExit = other.toExit;
        for (Point point : other.points) {
            this.points.add(new Point(point.x, point.y));
        }
    }

    public Point getFront() { return points.getLast(); }
    public Point getBack() { return points.getFirst(); }

    public void move(Direction direction) {
        for (Point point : points) {
            board.cells[point.y][point.x] = '.';
        }

        switch (direction) {
            case UP -> points.forEach(point -> point.y--);
            case DOWN -> points.forEach(point -> point.y++);
            case LEFT -> points.forEach(point -> point.x--);
            case RIGHT -> points.forEach(point -> point.x++);
        }

        for (Point point : points) {
            board.cells[point.y][point.x] = piece;
        }
    }

    public abstract List<Move> getMoves();

    public abstract boolean atGoal();
}