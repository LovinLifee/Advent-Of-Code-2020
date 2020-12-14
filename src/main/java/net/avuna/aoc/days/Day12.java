package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;
import net.avuna.aoc.util.Coordinate;
import net.avuna.aoc.util.Direction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static net.avuna.aoc.util.Direction.*;

public class Day12 extends Challenge<Object> {

    private List<String> lines = readLines().collect(Collectors.toList());
    private final Map<Character, Direction> directions = Map.of('N', NORTH, 'E', EAST, 'S', SOUTH, 'W', WEST);

    @Override
    public Object doPartOne() {
        Direction direction = Direction.EAST;
        Coordinate coordinate = new Coordinate(0, 0);
        for(String s : lines) {
            char opcode = s.charAt(0);
            int amount = Integer.parseInt(s.substring(1));
            switch(opcode) {
                case 'F' -> coordinate.move(direction, amount);
                case 'R' -> direction = direction.turn(amount);
                case 'L' -> direction = direction.turn(-amount);
                case 'N', 'E', 'S', 'W' -> coordinate.move(directions.get(opcode), amount);
                default -> throw new IllegalStateException("Unexpected value: " + opcode);
            };
        }
        return getDistance(0, 0, coordinate.getX(), coordinate.getY());
    }

    @Override
    public Object doPartTwo() {
        return null;
    }

    public static int getDistance(int x0, int y0, int x1, int y1) {
        return Math.abs(x1-x0) + Math.abs(y1-y0);
    }
}
