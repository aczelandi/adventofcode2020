package day11;

import common.Solution;

class SolutionImpl extends Solution<char[][], char[][], Integer, Integer> {

    private final int rowCount;

    private final int columnCount;

    public SolutionImpl(char[][] context1, char[][] context2, int rowCount, int columnCount) {
        super(context1, context2);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    @Override
    public Integer solvePart1() {
        var stabilizedSeatMapRule1 = this.stabilizeSeatMap(this.context1, rowCount, columnCount, true);
        return this.findSeatCountWithStatus(stabilizedSeatMapRule1, rowCount, columnCount, '#');
    }

    @Override
    public Integer solvePart2() {
        var stabilizedSeatMapRule2 = this.stabilizeSeatMap(this.context2, rowCount, columnCount, false);
        return this.findSeatCountWithStatus(stabilizedSeatMapRule2, rowCount, columnCount, '#');
    }

    private char[][] stabilizeSeatMap(char[][] seatMap, int rowCount, int columnCount, boolean rule1) {
        var finished = false;
        while (!finished) {
            char[][] tmpMap = new char[rowCount][columnCount];
            var changeCount = 0;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    var currentStatus = seatMap[i][j];
                    var newSeatStatus = getSeatStatusWithRule(seatMap, i, j, rowCount, columnCount, rule1);
                    if (currentStatus != newSeatStatus) {
                        changeCount++;
                    }
                    tmpMap[i][j] = newSeatStatus;
                }
            }
            finished = changeCount == 0;
            seatMap = tmpMap;
        }

        return seatMap;
    }

    private char getSeatStatusWithRule(char[][] seatMap, int currentRow, int currentColumn, int rowCount, int columnCount, boolean rule1) {
        var currentChar = seatMap[currentRow][currentColumn];
        if (currentChar == '.') return currentChar;
        if (currentChar == 'L') {
            var occupiedNeighbours = rule1 ? getOccupiedNeighboursRule1(seatMap, currentRow, currentColumn, rowCount, columnCount) :
                    getOccupiedNeighboursRule2(seatMap, currentRow, currentColumn, rowCount, columnCount);
            if (occupiedNeighbours == 0) {
                return '#';
            }
        } else if (currentChar == '#') {
            var occupiedNeighbours = rule1 ? getOccupiedNeighboursRule1(seatMap, currentRow, currentColumn, rowCount, columnCount) :
                    getOccupiedNeighboursRule2(seatMap, currentRow, currentColumn, rowCount, columnCount);
            var limit = rule1 ? 4 : 5;
            if (occupiedNeighbours >= limit) {
                return 'L';
            }
        }

        return currentChar;
    }

    private int getOccupiedNeighboursRule1(char[][] seatMap, int currentRow, int currentColumn, int rowCount, int columnCount) {
        var occupiedCount = 0;

        if (currentRow - 1 >= 0) {
            if (currentColumn - 1 >= 0) {
                if (seatMap[currentRow - 1][currentColumn - 1] == '#') occupiedCount++;
            }
            if (seatMap[currentRow - 1][currentColumn] == '#') occupiedCount++;
            if (currentColumn + 1 < columnCount) {
                if (seatMap[currentRow - 1][currentColumn + 1] == '#') occupiedCount++;
            }
        }

        if (currentColumn - 1 >= 0) {
            if (seatMap[currentRow][currentColumn - 1] == '#') occupiedCount++;
        }
        if (currentColumn + 1 < columnCount) {
            if (seatMap[currentRow][currentColumn + 1] == '#') occupiedCount++;
        }

        if (currentRow + 1 < rowCount) {
            if (currentColumn - 1 >= 0) {
                if (seatMap[currentRow + 1][currentColumn - 1] == '#') occupiedCount++;
            }
            if (seatMap[currentRow + 1][currentColumn] == '#') occupiedCount++;
            if (currentColumn + 1 < columnCount) {
                if (seatMap[currentRow + 1][currentColumn + 1] == '#') occupiedCount++;
            }
        }

        return occupiedCount;
    }

    private int getOccupiedNeighboursRule2(char[][] seatMap, int currentRow, int currentColumn, int rowCount, int columnCount) {
        var occupiedCount = 0;

        // up
        for (int i = currentRow - 1; i >= 0; i--) {
            var current = seatMap[i][currentColumn];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // top right diagonal
        for (int i = currentRow - 1, j = currentColumn + 1; i >= 0 && j < columnCount; i--, j++) {
            var current = seatMap[i][j];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // right
        for (int i = currentColumn + 1; i < columnCount; i++) {
            var current = seatMap[currentRow][i];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // bottom right diagonal
        for (int i = currentRow + 1, j = currentColumn + 1; i < rowCount && j < columnCount; i++, j++) {
            var current = seatMap[i][j];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // bottom
        for (int i = currentRow + 1; i < rowCount; i++) {
            var current = seatMap[i][currentColumn];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // bottom left diagonal
        for (int i = currentRow + 1, j = currentColumn - 1; i < rowCount && j >= 0; i++, j--) {
            var current = seatMap[i][j];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // left
        for (int i = currentColumn - 1; i >= 0; i--) {
            var current = seatMap[currentRow][i];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        // top left diagonal
        for (int i = currentRow - 1, j = currentColumn - 1; i >= 0 && j >= 0; i--, j--) {
            var current = seatMap[i][j];
            if (current == '#') {
                occupiedCount++;
                break;
            }
            if (current == 'L') {
                break;
            }
        }

        return occupiedCount;
    }

    private int findSeatCountWithStatus(char[][] seatMap, int rowCount, int columnCount, char status) {
        var count = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (seatMap[i][j] == status) {
                    count++;
                }
            }
        }

        return count;
    }
}
