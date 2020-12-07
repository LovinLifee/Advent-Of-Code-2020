package net.avuna.aoc.days;

import lombok.Value;
import net.avuna.aoc.Challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Challenge<Integer> {

    private static final Pattern bagPattern = Pattern.compile("(.+) bags contain (.+)\\.");
    private static final Pattern partPattern = Pattern.compile("(\\d+?) (.+?) bags?");

    private final Map<String, Bag> bags = parseBags();

    @Override
    public Integer doPartOne() {
        int count = 0;
        for(Bag bag : bags.values()) {
            if (leadsToGoldBag(bag)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Integer doPartTwo() {
        int count = 0;
        Bag b = bags.get("shiny gold");
        return countChildrenBags(b) - 1;
    }

    private Map<String, Bag> parseBags() {
        final Map<String, Bag> bags = new HashMap<>();
        readLines().forEach(line -> {
            Matcher bagMatcher = bagPattern.matcher(line);
            bagMatcher.matches();
            Bag bag = bags.computeIfAbsent(bagMatcher.group(1), Bag::new);
            String data = bagMatcher.group(2);
            if (data.equals("no other bags"))
                return;
            String[] split = data.split(", ");
            for (String part : split) {
                Matcher partMatcher = partPattern.matcher(part);
                partMatcher.matches();
                int num = Integer.parseInt(partMatcher.group(1));
                Bag child = bags.computeIfAbsent(partMatcher.group(2), Bag::new);
                bag.children.put(child.getColor(), num);
            }
        });
        return bags;
    }

    private boolean leadsToGoldBag(Bag bag) {
        Set<String> children = bag.children.keySet();
        for (String child : children) {
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