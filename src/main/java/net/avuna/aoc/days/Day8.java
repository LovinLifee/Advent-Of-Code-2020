package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 extends Challenge<Object> {

    private final List<String> lines = readLines().collect(Collectors.toList());

    @Override
    public Object doPartOne() {
        int accumulator = 0;
        Set<Integer> visited = new HashSet<>();
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] data = line.split(" ");
            String opcode = data[0];
            int value = Integer.parseInt(data[1]);
            switch (opcode) {
                case "acc" -> accumulator += value;
                case "jmp" -> i += value - 1;
            }
            if(visited.contains(i)) {
                break;
            }
            visited.add(i);
        }
        return accumulator;
    }

    @Override
    public Object doPartTwo() {
        for(int i = 0; i < lines.size(); i++) {
            List<String> lines2 = new ArrayList<>(lines);
            String line = lines2.get(i);
            String newLine = line;
            if(line.startsWith("jmp")) {
                newLine = "nop" + line.substring(3);
                lines2.set(i, newLine);
            }else if(line.startsWith("nop")) {
                newLine = "jmp" + line.substring(3);
                lines2.set(i, newLine);
            }
            if(terminates(lines2)) {
                System.out.printf("On line %d we replaced %s with %s\n", i, line + 1, newLine);
            }
        }
        return 0;
    }

    public boolean terminates(List<String> lines) {
        int accumulator = 0;
        Set<Integer> visited = new HashSet<>();
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] data = line.split(" ");
            String opcode = data[0];
            int value = Integer.parseInt(data[1]);
            switch (opcode) {
                case "acc" -> accumulator += value;
                case "jmp" -> i += value - 1;
            }
            if(i < 0 || i > lines.size() - 1) {
                return false;
            }
            if(visited.contains(i)) {
                return false;
            }
            visited.add(i);
        }
        return true;
    }
}
