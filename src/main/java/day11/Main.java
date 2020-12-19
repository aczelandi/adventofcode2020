package day11;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var seatMap = program.readInput("day11/input.txt", program::readInput);
        for (char[] chars : seatMap) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }

        var rowCount = program.readInput("day11/input.txt", program::getRowCount);
        var columnCount = program.readInput("day11/input.txt", program::getColumnCount);

        var cloneSeatMap1 = program.cloneSeatMap(seatMap, rowCount, columnCount);

        var stabilizedSeatMapRule1 = program.stabilizeSeatMap(cloneSeatMap1, rowCount, columnCount, true);
        var occupiedSeatCountRule1 = program.findSeatCountWithStatus(stabilizedSeatMapRule1, rowCount, columnCount, '#');
        System.out.println(occupiedSeatCountRule1);

        var cloneSeatMap2 = program.cloneSeatMap(seatMap, rowCount, columnCount);

        var stabilizedSeatMapRule2 = program.stabilizeSeatMap(cloneSeatMap2, rowCount, columnCount, false);
        var occupiedSeatCountRule2 = program.findSeatCountWithStatus(stabilizedSeatMapRule2, rowCount, columnCount, '#');
        System.out.println(occupiedSeatCountRule2);
    }

    private char[][] readInput(URI filePath) {
        var rowCount = this.getRowCount(filePath);
        var columnCount = this.getColumnCount(filePath);
        try (var scanner = new Scanner(new File(filePath))) {
            var seatMap = new char[rowCount][columnCount];
            var row = 0;
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                for (int i = 0; i < columnCount; i++) {
                    seatMap[row][i] = line.charAt(i);
                }
                row++;
            }

            return seatMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Could not load seatmap");
    }

    private int getRowCount(URI filePath) {
        var rowCount = 0;
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                rowCount++;
                scanner.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    private int getColumnCount(URI filePath) {

        try (var scanner = new Scanner(new File(filePath))) {
            return (int) scanner.nextLine().chars().count();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private char[][] stabilizeSeatMap(char[][] seatMap, int rowCount, int columnCount, boolean rule1) {
        var finished = false;
        while (!finished) {
            char[][] tmpMap = new char[rowCount][columnCount];
            var changeCount = 0;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    var currentStatus = seatMap[i][j];
                    var newSeatStatus = getSeatStatusRule(seatMap, i, j, rowCount, columnCount, rule1);
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

    private char getSeatStatusRule(char[][] seatMap, int currentRow, int currentColumn, int rowCount, int columnCount, boolean rule1) {
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

    private char[][] cloneSeatMap(char[][] seatMap, int rowCount, int columnCount) {
        var clone = new char[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            if (columnCount >= 0) System.arraycopy(seatMap[i], 0, clone[i], 0, columnCount);
        }

        return clone;
    }
}
