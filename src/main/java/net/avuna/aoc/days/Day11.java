package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;
import net.avuna.aoc.Matrix2D;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Day11 extends Challenge<Object> {

    private Matrix2D<Character> matrix = new Matrix2D(readAsMatrix());

    @Override
    public Object doPartOne() {
        Set<Matrix2D<Character>> seen = new HashSet<>();
        while(!seen.contains(matrix)) {
            seen.add(matrix);
            doStep();
        }
        AtomicLong occupiedSeats = new AtomicLong();
        matrix.forEach((x, y, c) -> {
            occupiedSeats.getAndIncrement();
        });
        return occupiedSeats;
    }

    public void doStep() {
        Matrix2D<Character> temp = new Matrix2D<>(matrix.getWidth(), matrix.getHeight());
        System.out.println(matrix.getWidth());
        System.out.println(matrix.getHeight());
        matrix.forEach((x, y, c) -> {
            long occupiedAdjacentSeats = matrix.getAdjacentElements(x, y).stream().filter(s -> s == '#').count();
            char toSet = c;
            if(c == 'L' && occupiedAdjacentSeats == 0) {
                toSet = '#';
            } else if(c == '#' && occupiedAdjacentSeats >= 4) {
                toSet = 'L';
            }
            temp.set(x, y, toSet);
        });
        this.matrix = temp;
    }

    private void printMatrix() {
        matrix.forEach((x, y, c) -> {
            System.out.print(c);
        }, () -> {
            System.out.println();
        });
    }

    @Override
    public Object doPartTwo() {
        return null;
    }
}
