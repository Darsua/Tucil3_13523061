package Game;

import Game.Piece.*;

import java.util.*;

public class Board {
    public final char[][] cells;
    public final HashMap<Character, Piece> pieces;
    public final Point exit;

    public Board(int rows, int cols, char[][] input, Point exit) {
        this.cells = new char[rows][cols];
        this.exit = exit;

        pieces = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char pc = input[i][j];

                if (pc != '.') {
                    if (!pieces.containsKey(pc)) {
                        // Create new piece if a new character is found
                        if (i + 1 < rows && input[i + 1][j] == pc)
                            pieces.put(pc, new Vertical(pc, this, j, i));
                        else if (j + 1 < cols && input[i][j + 1] == pc)
                            pieces.put(pc, new Horizontal(pc, this, j, i));
                    }
                    else // Add point to existing piece
                        pieces.get(pc).points.add(new Point(j, i));
                }
                cells[i][j] = pc;
            }
        }
    }

    public Board(Board other) {
        this.cells = new char[other.y()][other.x()];
        for (int i = 0; i < y(); i++) {
            System.arraycopy(other.cells[i], 0, this.cells[i], 0, x());
        }

        this.pieces = new HashMap<>();
        for (Map.Entry<Character, Piece> entry : other.pieces.entrySet()) {
            Piece original = entry.getValue();
            Piece copy;
            if (original instanceof Horizontal) {
                copy = new Horizontal((Horizontal) original, this);
            } else if (original instanceof Vertical) {
                copy = new Vertical((Vertical) original, this);
            } else {
                throw new IllegalStateException("Unknown piece type");
            }
            this.pieces.put(entry.getKey(), copy);
        }

        this.exit = other.exit;
    }

    public int y() {return cells.length;}

    public int x() {return cells[0].length;}

    public int getPieceCount() { return pieces.size(); }

    public Piece getPiece(char piece) { return pieces.get(piece); }

    public char getCell(int x, int y) { return cells[y][x]; }

    public boolean emptyAt(int x, int y) {
        if (x < 0 || y < 0 || x >= x() || y >= y()) return false;
        return cells[y][x] == '.';
    }

    public void apply(Move move) {
        Piece piece = pieces.get(move.piece());
        if (piece == null) return;

        // Move the piece
        piece.move(move.direction());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < y() + 2; i++) {
            for (int j = 0; j < x() + 2; j++) {
                if (i == exit.y + 1 && j == exit.x + 1) builder.append(' ');
                else if (i == 0 || i == y() + 1 || j == 0 || j == x() + 1) builder.append('█');
                else builder.append(cells[i - 1][j - 1]);
            }
            if (i < y() + 1) builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board other = (Board) obj;
        return Objects.equals(this.pieces, other.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces);
    }
}
