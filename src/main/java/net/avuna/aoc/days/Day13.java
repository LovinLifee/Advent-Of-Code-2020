package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends Challenge<Object> {

    private final List<String> lines = readLines().collect(Collectors.toList());

    @Override
    public Object doPartOne() {
        long earliestDeparture = Long.parseLong(lines.get(0));
        var busses= Arrays.stream(lines.get(1).split(",")).filter(s -> !s.equals("x")).map(Integer::parseInt).collect(Collectors.toList());
        long earliestBusDeparture = Long.MAX_VALUE;
        long earliestBusId = 0;
        for(int busId : busses) {
            //There HAS to be a better way, I just absolutely SUCK at math.
            for(int i = 1; i < 25000; i++) {
                long departureTime = busId * i;
                if(departureTime >= earliestDeparture && departureTime < earliestBusDeparture) {
                    earliestBusDeparture = departureTime;
                    earliestBusId = busId;
                }
            }
        }
        long timeToDeparture = Math.abs(earliestDeparture - earliestBusDeparture);
        return timeToDeparture * earliestBusId;
    }

    @Override
    public Object doPartTwo() {
        return null;
    }
}
