package net.avuna.aoc;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    protected String[] readAsCSV() {
       return read().split(",");
    }
}
