package net.avuna.aoc.days;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.aoc.Challenge;

import java.util.List;
import java.util.stream.Collectors;

public class Day5 extends Challenge<Integer> {

    private final List<Seat> seats = parseSeats();

    public Day5() {
        super(5);
    }

    private List<Seat> parseSeats() {
        return readLines().map(s -> {
            String row = s.substring(0, 7);
            String column = s.substring(7);
            int rowNumber = getSeatRow(row);
            int columnNumber = getSeatColumn(column);
            int seatId = rowNumber * 8 + columnNumber;
            return new Seat(rowNumber, columnNumber, seatId);
        }).collect(Collectors.toList());
    }

    @Override
    public Integer doPartOne() {
        int highestSeatId = Integer.MIN_VALUE;
        for(Seat seat : seats) {
            if(seat.getId() > highestSeatId) {
                highestSeatId = seat.getId();
            }
        }
        return highestSeatId;
    }

    private int getSeatRow(String input) {
        return binarySearch(input, 'F', 'B', 0, 127);
    }

    private int getSeatColumn(String input) {
        return binarySearch(input, 'L', 'R', 0, 7);
    }

    private int binarySearch(String input, final char lowerHalf, final char upperHalf, float min, float max) {
        for(int i = 0; i < input.length(); i++) {
            char opcode = input.charAt(i);
            if(opcode == upperHalf) {
                min = (float) Math.ceil((min + max) / 2F);
            } else if(opcode == lowerHalf) {
                max = (float) Math.ceil((min + max) / 2F);
            }
        }
        return Math.round(Math.min(min, max));
    }

    /*
        Checking where our seat is located can be done in O(n) as apposed to O(n^2)
        Since the numbers in the list are sequential, we can check for the first occurrence
        where the current number and the previous number have a difference of 2
        I'm just lazy and this is much prettier.
     */
    @Override
    public Integer doPartTwo() {
        List<Integer> seatIds = seats.stream().map(Seat::getId).sorted().collect(Collectors.toList());
        int yourSeatId = -1;
        for(int i = seatIds.get(0); i < seatIds.get(seatIds.size() - 1); i++) {
            if(!seatIds.contains(i)) {
                yourSeatId = i;
                break;
            }
        }
        return yourSeatId;
    }


    @Getter
    @RequiredArgsConstructor
    private static final class Seat {
        private final int row;
        private final int column;
        private final int id;
    }
}
