package net.avuna.aoc.days;

import lombok.Value;
import net.avuna.aoc.Challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day7 extends Challenge<Object> {

    private final Map<String, Bag> bags = parseBags();

    @Override
    public Object doPartOne() {
        return bags.values().stream().filter(this::leadsToGoldBag).count();
    }

    @Override
    public Object doPartTwo() {
        return countChildrenBags(bags.get("shiny gold")) - 1;
    }

    private Map<String, Bag> parseBags() {
        final Map<String, Bag> bags = new HashMap<>();
        readLines().forEach(line -> {
            String[] parts = line.split(" bags contain ");
            Bag bag = bags.computeIfAbsent(parts[0], Bag::new);
            String[] childParts = parts[1].split(", ");
            for(String s : childParts) {
                if(s.startsWith("no"))
                    continue;
                int amount = Integer.parseInt(s.substring(0, 1));
                String childColor = s.substring(2, s.length() - (amount > 1 ? 5 : 4)).trim();
                bag.children.put(childColor, amount);
            }
        });
        return bags;
    }

    private boolean leadsToGoldBag(Bag bag) {
        Set<String> children = bag.children.keySet();
        for(String child : children) {
            if (leadsToGoldBag(bags.get(child))) {
                return true;
            }
        }
        return children.contains("shiny gold");
    }

    private int countChildrenBags(Bag bag) {
        int sum = 1;
        for (Map.Entry<String, Integer> e : bag.getChildren().entrySet()) {
            sum += e.getValue() * countChildrenBags(bags.get(e.getKey()));
        }
        return sum;
    }

    @Value
    private static class Bag {
        String color;
        Map<String, Integer> children = new HashMap<>();
    }
}