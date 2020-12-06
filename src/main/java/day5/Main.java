package day5;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main extends BaseMain {

    private static final int MAX_SEATS_PER_ROW = 8;
    private static final int MAX_ROWS_CHARS = 7;

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var input = program.readInput("day5/input.txt", program::readSeats);
        System.out.println(input);

        var highestSeatId = input
                .stream()
                .map(SeatInfo::getSeatId)
                .max(Integer::compare)
                .orElse(0);

        System.out.println(highestSeatId);

        var sortedById = input
                .stream()
                .map(SeatInfo::getSeatId)
                .sorted()
                .filter(x -> !inFirstRow(x) && !inLastRow(x))
                .collect(Collectors.toList());

        var mySeat = 0;
        var prev = sortedById.get(0);
        for (int i = 1; i < sortedById.size(); i++) {
            var current = sortedById.get(i);
            if ((current - prev) == 2) {
                mySeat = current - 1;
                break;
            } else {
                prev = current;
            }
        }

        System.out.println(mySeat);
    }

    private List<SeatInfo> readSeats(URI filePath) {
        var seats = new ArrayList<SeatInfo>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var seatCode = sc.nextLine();
                var seat = convertCodeToSeat(seatCode);
                seats.add(seat);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return seats;
    }

    private SeatInfo convertCodeToSeat(String seatCode) {
        var minRow = 0;
        var maxRow = (int) (Math.pow(2, MAX_ROWS_CHARS) - 1);
        for (int i = 0; i < MAX_ROWS_CHARS; i++) {
            var currentChar = seatCode.charAt(i);
            if (currentChar == 'F') {
                var half = getMiddle(minRow, maxRow, true);
                maxRow = minRow + half;
            } else {
                var half = getMiddle(minRow, maxRow, false);
                minRow += half;
            }
        }

        var minTotalSeats = 0;
        var maxTotalSeats = MAX_SEATS_PER_ROW - 1;
        for (int i = MAX_ROWS_CHARS; i < MAX_ROWS_CHARS + 3; i++) {
            var currentChar = seatCode.charAt(i);
            if (currentChar == 'L') {
                var half = getMiddle(minTotalSeats, maxTotalSeats, true);
                maxTotalSeats = minTotalSeats + half;
            } else {
                var half = getMiddle(minTotalSeats, maxTotalSeats, false);
                minTotalSeats += half;
            }
        }

        return new SeatInfo(maxRow, maxTotalSeats);
    }

    private int getMiddle(int min, int max, boolean lower) {
        if (lower) {
            return (max - min) / 2;
        } else {
            return (max - min) / 2 + 1;
        }
    }

    private static boolean inFirstRow(int seatId) {
        return seatId < MAX_SEATS_PER_ROW;
    }

    private static boolean inLastRow(int seatId) {
        return seatId >= Math.pow(2, MAX_ROWS_CHARS) * MAX_SEATS_PER_ROW;
    }

    private record SeatInfo(int row, int seat) {

        public int getSeatId() {
            return row * MAX_SEATS_PER_ROW + seat;
        }
    }
}
