package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.stream.Collectors;


//HECK this challenge.
public class Day10 extends Challenge<Object> {

    private List<Integer> voltageAdapters = readLines().map(Integer::parseInt).collect(Collectors.toList());

    @Override
    public Object doPartOne() {
        int builtInVoltageAdapterRating = voltageAdapters.stream().mapToInt(i -> i).max().getAsInt() + 3;
        System.out.println(builtInVoltageAdapterRating);
        for(int adapterRating : voltageAdapters) {

        }
        return null;
    }

    private static boolean isAdapterRatedFor(int adapterRating, int voltage) {
        return voltage >= adapterRating - 3 && voltage <= adapterRating;
    }

    @Override
    public Object doPartTwo() {
        return null;
    }
}
