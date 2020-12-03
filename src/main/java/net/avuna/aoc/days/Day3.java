package net.avuna.aoc.days;

import lombok.*;
import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends Challenge<Long> {

    private static final char TREE = '#';

    public Day3() {
        super(3);
    }

    @Override
    public Long doPartOne() {
        Map map = createMap();
        return countTreesOnSlope(map, 3, 1);
    }

    private Map createMap() {
        List<String> input = readLines().collect(Collectors.toList());
        char[][] matrix = new char[input.get(0).length()][input.size()];
        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(x).length(); y++) {
                matrix[y][x] = input.get(x).charAt(y);
            }
        }
        Map map = new Map(matrix);
        return map;
    }

    @Override
    public Long doPartTwo() {
        Map map = createMap();
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
    private static final class Position {

        private int x;
        private int y;

        public void moveRelative(int x, int y) {
            this.x += x;
            this.y += y;
        }
    }
}
