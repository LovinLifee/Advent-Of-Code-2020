package net.avuna.aoc.days;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 extends Challenge<Long> {

    private final List<Data> data = parseData().collect(Collectors.toList());

    public Day2() {
        super(2);
    }

    @Override
    public Long doPartOne() {
        return data.stream().filter(this::isValidPart1).count();
    }

    @Override
    public Long doPartTwo() {
        return data.stream().filter(this::isValidPart2).count();
    }

    private boolean isValidPart1(Data data) {
        long occurrences = data.getPassword().chars().filter(c -> c == data.getCharToCheck()).count();
        return occurrences >= data.getMin() && occurrences <= data.getMax();
    }

    private boolean isValidPart2(Data data) {
        return data.getPassword().charAt(data.getMin() - 1) == data.getCharToCheck()
                ^ data.getPassword().charAt(data.getMax() - 1) == data.getCharToCheck();
    }

    private Stream<Data> parseData() {
        return readLines().map(s -> {
            String[] data = s.split(" ");
            String[] range = data[0].split("-");
            int min = Integer.parseInt(range[0]);
            int max = Integer.parseInt(range[1]);
            char charToCheck = data[1].charAt(0);
            String password = data[2];
            return new Data(charToCheck, min, max, password);
        });
    }

    @Getter
    @RequiredArgsConstructor
    private static final class Data {
        private final char charToCheck;
        private final int min;
        private final int max;
        private final String password;
    }
}
