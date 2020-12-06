package net.avuna.aoc.days;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.aoc.Challenge;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
    @Author Laziest Programmer Ever

    I'm no Eisenstein, but these cannot be efficient at all lmao.

    Also note: I've prematurely given up on the bug where you don't read the last group,
    so to circumvent this just add a few newlines to the bottom of your text document
 */
public class Day6 extends Challenge<Object> {

    private final List<String> lines = readLines().collect(Collectors.toList());

    public Day6() {
        super(6);
    }

    @Override
    public Object doPartOne() {
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
    public Object doPartTwo() {
        int sum = 0;
        int groupSize = 0;
        FrequencyMap<Character> occurrences = new FrequencyMap<>();
        for(int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            if(s.isBlank()) {
                for(AtomicInteger num : occurrences.backingMap.values()) {
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

    /*
        Who knew copying and pasting from your previous projects would be useful?
     */
    private static class FrequencyMap<T> {

        private final Map<T, AtomicInteger> backingMap = new HashMap<>();

        public void clear() {
            backingMap.clear();
        }

        public int increment(T key) {
            AtomicInteger count = backingMap.computeIfAbsent(key, k -> new AtomicInteger());
            return count.incrementAndGet();
        }

        public int getCount(T key) {
            return backingMap.get(key).get();
        }

        public int getTotalCount() {
            return backingMap.entrySet().stream().mapToInt(e -> e.getValue().get()).sum();
        }

        public Tuple<T, Integer> getMin() {
            Map.Entry<T, AtomicInteger> entry = backingMap.entrySet().stream().min(Comparator.comparingInt(o -> o.getValue().get())).orElseThrow(NoSuchElementException::new);
            return Tuple.of(entry.getKey(), entry.getValue().get());
        }

        public Tuple<T, Integer> getMax() {
            Map.Entry<T, AtomicInteger> entry = backingMap.entrySet().stream().max(Comparator.comparingInt(o -> o.getValue().get())).orElseThrow(NoSuchElementException::new);
            return Tuple.of(entry.getKey(), entry.getValue().get());
        }

        public double getAverageCount() {
            return backingMap.entrySet().stream().mapToInt(e -> e.getValue().get()).average().orElseThrow(NoSuchElementException::new);
        }

        public void forEach(BiConsumer<T, Integer> action) {
            backingMap.entrySet().iterator().forEachRemaining(e -> action.accept(e.getKey(), e.getValue().get()));
        }
    }


    @Getter
    @RequiredArgsConstructor
    private static class Tuple<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("Tuple is immutable");
        }

        public static <K, V> Tuple<K, V> of(K key, V value) {
            return new Tuple<>(key, value);
        }
    }
}
