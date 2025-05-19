package Algorithm.Heuristic;

import Algorithm.State;

public class BlockingDistance {
    public static int calculate(State state) {
        return ExitDistance.calculate(state) + BlockingPieces.calculate(state);
    }
}
