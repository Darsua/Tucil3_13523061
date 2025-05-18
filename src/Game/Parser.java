package Game;

import Game.Pieces.*;

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
            throw new IllegalArgumentException("Invalid dimensions: " + line);
        int x = Integer.parseInt(dimensions[0]);
        int y = Integer.parseInt(dimensions[1]);

        // Read banyak piece (selain primary)
        line = br.readLine();
        try {
            int n = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number of pieces: " + line);
        }

        // Read board configuration
        char[][] bufferBoard = new char[y][x];
        int exitX = 0, exitY = 0, i = 0;

        while ((line = br.readLine()) != null) {

            if (line.contains(exitChar + "")) {
                exitY = i;
                exitY += exitY > 0 ? 1 : 0;

                exitX = line.indexOf(exitChar);
                exitX += exitX > 0 ? 1 : 0;

                if (i == 0 || i >= y) continue;
            }

            line = line.replace(" ", "").replace(exitChar + "", "");
            bufferBoard[i] = line.toCharArray();
            i++;
        }

        // Load into board
        Board board = new Board(x, y, bufferBoard, new Point(exitX, exitY));
        System.out.print(board);
        return board;
    }
}
