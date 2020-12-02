package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Day1 extends Challenge<Integer> {

    private final List<Integer> numbers = readLines().map(Integer::parseInt).collect(Collectors.toList());

    public Day1() {
        super(1);
    }

    @Override
    public Integer doPartOne() {
        for(int a : numbers) {
            for(int b : numbers) {
                if(a + b == 2020) {
                    return a * b;
                }
            }
        }
        throw new NoSuchElementException("Part one could not be solved.");
    }

    @Override
    public Integer doPartTwo() {
        for(int a : numbers) {
            for(int b : numbers) {
                for(int c : numbers) {
                    if(a + b + c == 2020) {
                        return a * b * c;
                    }
                }
            }
        }
        throw new NoSuchElementException("Part two could not be solved.");
    }
}
