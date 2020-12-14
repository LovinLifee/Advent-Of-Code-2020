package net.avuna.aoc.util;

import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/*
        Who knew copying and pasting from your previous projects would be useful?
     */
@Getter
public class FrequencyMap<T> {

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