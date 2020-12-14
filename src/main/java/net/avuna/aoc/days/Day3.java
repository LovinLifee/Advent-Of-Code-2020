package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;
import net.avuna.aoc.util.Coordinate;
import net.avuna.aoc.util.Matrix2D;

public class Day3 extends Challenge<Long> {

    private static final char TREE = '#';

    private final Matrix2D<Character> map = new Matrix2D(readAsMatrix());

    @Override
    public Long doPartOne() {
        return countTreesOnSlope(map, new Coordinate(3, 1));
    }

    @Override
    public Long doPartTwo() {
        long treesOnSlopeA = countTreesOnSlope(map, new Coordinate(1, 1));
        long treesOnSlopeB = countTreesOnSlope(map, new Coordinate(3, 1));
        long treesOnSlopeC = countTreesOnSlope(map, new Coordinate(5, 1));
        long treesOnSlopeD = countTreesOnSlope(map, new Coordinate(7, 1));
        long treesOnSlopeE = countTreesOnSlope(map, new Coordinate(1, 2));
        return treesOnSlopeA * treesOnSlopeB * treesOnSlopeC * treesOnSlopeD * treesOnSlopeE;
    }

    public long countTreesOnSlope(Matrix2D<Character> map, Coordinate offset) {
        Coordinate position = new Coordinate(0, 0);
        long trees = 0;
        while(map.isInBounds(position)) {
            if(map.get(position) == TREE) {
                trees++;
            }
            position.move(offset);
            position.setX(position.getX() % map.getWidth());
        }
        return trees;
    }
}
