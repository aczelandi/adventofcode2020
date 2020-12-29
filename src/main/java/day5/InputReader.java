package day5;

import common.BaseInputReader;
import day5.model.SeatInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;

import static day5.Constants.MAX_ROWS_CHARS;
import static day5.Constants.MAX_SEATS_PER_ROW;

class InputReader extends BaseInputReader<List<SeatInfo>> {

    @Override
    protected List<SeatInfo> read(URI filePath) throws FileNotFoundException {
        var seats = new ArrayList<SeatInfo>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var seatCode = sc.nextLine();
                var seat = convertCodeToSeat(seatCode);
                seats.add(seat);
            }
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
}
