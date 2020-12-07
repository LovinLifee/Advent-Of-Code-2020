package net.avuna.aoc.days;

import lombok.Value;
import net.avuna.aoc.Challenge;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day7 extends Challenge<Object> {

    public Day7() {
        super(7);
    }

    private final Map<String, Set<String>> bags = new HashMap<>();

    @Override
    public Object doPartOne() {
        AtomicInteger count = new AtomicInteger();
        readLines().forEach(s -> {
            String[] parts = s.split("contain");
            String currentBag = parts[0].trim();
            String bagColour = getBagColour(currentBag);
            Set<String> children = bags.computeIfAbsent(bagColour, bags -> new HashSet<>());
            String[] childBagData = parts[1].split(",");
            for(String b : childBagData) {
                String color = getBagColour(b);
                children.add(color);
            }
            this.bags.put(bagColour, children);
        });
        this.bags.forEach((bag, children) -> {
            if(leadsToGoldBag(bag)) {
                count.incrementAndGet();
            }
        });
        return count;
    }

    private boolean leadsToGoldBag(String bag) {
        Set<String> children = this.bags.get(bag);
        if(children == null) {
            return false;
        }
        for(String child : children) {
            if(leadsToGoldBag(child)) {
                return true;
            }
        }
        return children.contains("shiny gold");
    }

    @Override
    public Object doPartTwo() {
        return null;
    }

    private static int getBagAmount(String bagData) {
        String amount = bagData.replaceAll("\\D+", "");
        return Integer.parseInt(amount);
    }

    private String getBagColour(String bagData) {
        String bag = bagData;
        bag = bag.replaceAll("\\d+", "");
        bag = bag.replaceAll("bags", "");
        bag = bag.replaceAll("bag", "");
        bag = bag.replaceAll("\\.+", "");
        return bag.trim();
    }
}
