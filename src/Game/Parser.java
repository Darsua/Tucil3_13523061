package Game;

import Game.Piece.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private static final char exitChar = 'K';

    public static Board parse(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));

        // Read board dimensions
        String line = br.readLine();
        String[] dimensions = line.split(" ");
        if (dimensions.length != 2)
            throw new IllegalArgumentException("Invalid dimensions: " + line + " (line 1)");
        int y = Integer.parseInt(dimensions[0]);
        int x = Integer.parseInt(dimensions[1]);

        // Read banyak piece (selain primary)
        line = br.readLine();
        int n;
        try {
            n = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number of pieces: " + line + (" (line 2)"));
        }

        // Read board configuration
        char[][] bufferBoard = new char[y][x];
        int exitX = -1, exitY = -1, i = 0;

        while ((line = br.readLine()) != null) {

            if (line.contains(exitChar + "")) {
                if ((exitY != -1 && exitX != -1) || line.length()-line.replace(exitChar + "", "").length() > 1)
                    throw new IllegalArgumentException("Multiple exit points found: " + line + " (line " + (i + 3) + ")");

                exitY = i;
                exitY += exitY > 0 ? 1 : 0;

                exitX = line.indexOf(exitChar);
                exitX += exitX > 0 ? 1 : 0;

                // It's hard to check if the exit is inside the board or not because of the spaces
                // TODO: Check if exit is inside the board

                if (i == 0 || i >= y) continue;
            }

            if (i == y) throw new IllegalArgumentException("Too many rows: " + line + " (line " + (i + 3) + ")");

            String trimLine = line.replace(" ", "").replace(exitChar + "", "");

            if(trimLine.length() != x)
                throw new IllegalArgumentException("Invalid row: " + line + " (line " + (i + 3) + ")");

            bufferBoard[i] = trimLine.toCharArray();
            i++;
        }

        // Load into board
        Board board = new Board(y, x, bufferBoard, new Point(exitX - 1, exitY - 1));

        if (board.getPieceCount() != n)
            throw new IllegalArgumentException("Invalid number of pieces: " + n + " != " + board.getPieceCount());

        return board;
    }
}
