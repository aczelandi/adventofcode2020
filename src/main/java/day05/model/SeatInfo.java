package day05.model;

import static day05.Constants.MAX_ROWS_CHARS;
import static day05.Constants.MAX_SEATS_PER_ROW;

public record SeatInfo(int row, int seat) {

    public int getSeatId() {
        return row * MAX_SEATS_PER_ROW + seat;
    }

    public boolean inFirstRow() {
        return getSeatId() < MAX_SEATS_PER_ROW;
    }

    public boolean inLastRow() {
        return getSeatId() >= Math.pow(2, MAX_ROWS_CHARS) * MAX_SEATS_PER_ROW;
    }

}