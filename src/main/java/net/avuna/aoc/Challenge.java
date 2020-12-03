package net.avuna.aoc;

import lombok.Getter;
import net.avuna.aoc.days.Day3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public abstract class Challenge<T> {

    private final int day;

    public Challenge(int day) {
        this.day = day;
    }

    public abstract T doPartOne();
    public abstract T doPartTwo();

    protected Stream<String> readLines() {
        String resourceName = String.format("day-%d-input.txt", day);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)));
        return reader.lines();
    }

    protected String read() {
        return readLines().collect(Collectors.joining("\n"));
    }

    protected void operateOnCSV(Consumer<String[]> consumer) {
        readLines().map(s -> s.split(",")).forEach(csv -> {
            consumer.accept(csv);
        });
    }

    protected char[][] readAsMatrix() {
        List<String> input = readLines().collect(Collectors.toList());
        char[][] matrix = new char[input.get(0).length()][input.size()];
        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(x).length(); y++) {
                matrix[y][x] = input.get(x).charAt(y);
            }
        }
        return matrix;
    }

    protected String[] readAsCSV() {
       return read().split(",");
    }
}
