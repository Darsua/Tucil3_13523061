package Game;

import Game.Pieces.*;

import java.util.HashMap;

public class Board {
    private final boolean[][] occupied;
    private final HashMap<Character, Piece> pieces;
    private final Point exit;

    public Board(int rows, int cols, char[][] cells, Point exit) {
        // +2 for walls
        occupied = new boolean[rows + 2][cols + 2];
        for (int i = 0; i < y(); i++) {
            for (int j = 0; j < x(); j++) {
                occupied[i][j] = i == 0 || j == 0 || i == y() - 1 || j == x() - 1;
                // Make the walls occupied
            }
        }

        pieces = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char pc = cells[i][j];

                if (pc == '.') continue;

                if (!pieces.containsKey(pc)) {
                    // Create new piece if a new character is found
                    if (i + 1 < rows && cells[i + 1][j] == pc)
                        pieces.put(pc, new PieceVertical());
                    else if (j + 1 < cols && cells[i][j + 1] == pc)
                        pieces.put(pc, new PieceHorizontal());
                }
                // Add point to that piece
                pieces.get(pc).points.add(new Point(j, i));
                occupied[i + 1][j + 1] = true;
            }
        }

        this.exit = exit;
    }

    private int y() {return occupied.length;}
    private int x() {return occupied[0].length;}

    public String toString() {
        char[][] cells = new char[y()][x()];
        for (int i = 0; i < y(); i++) {
            for (int j = 0; j < x(); j++) {
                cells[i][j] = occupied[i][j] ? '=' : '.'; // Wall : Empty
            }
        }

        cells[exit.y][exit.x] = ' ';

        for (char pc : pieces.keySet()) {
            for (Point p : pieces.get(pc).points) {
                cells[p.y + 1][p.x + 1] = pc;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < y(); i++) {
            for (int j = 0; j < x(); j++) {
                builder.append(cells[i][j]).append(" ");
            }
            if (i < y() - 1) builder.append("\n");
        }
        return builder.toString();
    }
}
