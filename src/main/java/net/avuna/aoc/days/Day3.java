package net.avuna.aoc.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.avuna.aoc.Challenge;

public class Day3 extends Challenge<Long> {

    private static final char TREE = '#';

    private final Map map = new Map(readAsMatrix());

    public Day3() {
        super(3);
    }

    @Override
    public Long doPartOne() {
        return countTreesOnSlope(map, 3, 1);
    }

    @Override
    public Long doPartTwo() {
        long treesOnSlopeA = countTreesOnSlope(map, 1, 1);
        long treesOnSlopeB = countTreesOnSlope(map, 3, 1);
        long treesOnSlopeC = countTreesOnSlope(map, 5, 1);
        long treesOnSlopeD = countTreesOnSlope(map, 7, 1);
        long treesOnSlopeE = countTreesOnSlope(map, 1, 2);
        return treesOnSlopeA * treesOnSlopeB * treesOnSlopeC * treesOnSlopeD * treesOnSlopeE;
    }

    public long countTreesOnSlope(Map map, int xMove, int yMove) {
        Position position = new Position(0, 0);
        long trees = 0;
        while(map.contains(position)) {
            if(map.containsTreeAt(position)) {
                trees++;
            }
            position.moveRelative(xMove, yMove);
            position.setX(position.getX() % map.WIDTH);
        }
        return trees;
    }

    private static final class Map {

        private final char[][] matrix;
        private final int WIDTH;
        private final int HEIGHT;

        public Map(char[][] matrix) {
            this.matrix = matrix;
            this.WIDTH = matrix.length;
            this.HEIGHT = matrix[0].length;
        }

        public boolean contains(Position position) {
            int x = position.getX();
            int y = position.getY();
            return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
        }

        public boolean containsTreeAt(Position position) {
            int x = position.getX();
            int y = position.getY();
            return matrix[x][y] == TREE;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    private final class Position {

        private int x;
        private int y;

        public void moveRelative(int x, int y) {
            this.x += x;
            this.y += y;
        }
    }
}
