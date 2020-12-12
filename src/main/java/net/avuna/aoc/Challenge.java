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

    public abstract T doPartOne();
    public abstract T doPartTwo();

    protected Stream<String> readLines() {
        int day = Integer.parseInt(getClass().getSimpleName().substring(3));
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

    protected Character[][] readAsMatrix() {
        List<String> input = readLines().collect(Collectors.toList());
        Character[][] matrix = new Character[input.get(0).length()][input.size()];
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                matrix[x][y] = input.get(y).charAt(x);
            }
        }
        return matrix;
    }

    protected String[] readAsCSV() {
       return read().split(",");
    }
}
