package net.avuna.aoc.days;

import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

public class Day9 extends Challenge<Long> {

    private final List<Long> check = readLines().map(Long::parseLong).distinct().collect(Collectors.toList());
    private long partOneAnswer;

    @Override
    public Long doPartOne() {
        partOneAnswer = check.stream().skip(25).filter(i -> !isSumOfAnyTwoIntegers(i, check)).findAny().get();
        return partOneAnswer;
    }

    @Override
    public Long doPartTwo() {
        long invalid = partOneAnswer;
        int startIndex = 0;
        int stopIndex = 1;
        long sum = check.get(startIndex) + check.get(stopIndex);
        while (sum != invalid) {
            if (sum < invalid) {
                stopIndex++;
                sum += check.get(stopIndex);
            } else {
                sum -= check.get(startIndex);
                startIndex++;
            }
        }
        LongSummaryStatistics stats = check.subList(startIndex, stopIndex + 1).stream().mapToLong(i -> i).summaryStatistics();
        return stats.getMax() + stats.getMin();
    }

    private boolean isSumOfAnyTwoIntegers(long value, List<Long> check) {
        for(long a : check)
            for(long b : check)
                if(a + b == value)
                    return true;
        return false;
    }
}
