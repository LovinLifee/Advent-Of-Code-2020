package net.avuna.aoc;

import net.avuna.aoc.days.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdventCalender {

    private static final List<Challenge<?>> DAYS = new ArrayList<>();

    static {
        DAYS.add(new Day1());
        DAYS.add(new Day2());
        DAYS.add(new Day3());
        DAYS.add(new Day4());
        DAYS.add(new Day5());
        DAYS.add(new Day6());
        DAYS.add(new Day7());
        DAYS.add(new Day8());
        DAYS.add(new Day9());
        DAYS.add(new Day10());
        DAYS.add(new Day11());
        DAYS.add(new Day12());
        DAYS.add(new Day13());
    }

    private static void runDay(Challenge<?> challenge) {
        System.out.printf("----------------- %s -----------------\n", challenge.getClass().getSimpleName());
        System.out.printf("Output of part 1: %s\n", Objects.toString(challenge.doPartOne()));
        System.out.printf("Output of part 2: %s\n", Objects.toString(challenge.doPartTwo()));
    }

    public static void runDay(int day) {
        var challenge = DAYS.get(day - 1);
        runDay(challenge);
    }

    public static void runAllDays() {
        DAYS.forEach(AdventCalender::runDay);
    }
}
