package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;
import net.avuna.aoc.util.FrequencyMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
    @Author Laziest Programmer Ever

    I'm no Eisenstein, but these cannot be efficient at all lmao.

    Also note: I've prematurely given up on the bug where you don't read the last group,
    so to circumvent this just add a few newlines to the bottom of your text document
 */
public class Day6 extends Challenge<Integer> {

    private final List<String> lines = readLines().collect(Collectors.toList());

    @Override
    public Integer doPartOne() {
        Set<Character> characters = new HashSet<>();
        int sum = 0;
        for(int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            if(s.isBlank()) {
                sum += characters.size();
                characters.clear();
            } else {
                for(char c : s.toCharArray()) {
                    characters.add(c);
                }
            }
        }
        sum += characters.size();
        return sum;
    }

    @Override
    public Integer doPartTwo() {
        int sum = 0;
        int groupSize = 0;
        FrequencyMap<Character> occurrences = new FrequencyMap<>();
        for(int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            if(s.isBlank()) {
                for(AtomicInteger num : occurrences.getBackingMap().values()) {
                    if(num.get() == groupSize) {
                        sum++;
                    }
                }
                groupSize = 0;
                occurrences.clear();
            } else {
                groupSize++;
                for(char c : s.toCharArray()) {
                    occurrences.increment(c);
                }
            }
        }
        return sum;
    }
}
