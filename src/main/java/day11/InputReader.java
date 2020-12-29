package day11;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

public class InputReader extends BaseInputReader<char[][]> {

    @Override
    protected char[][] read(URI filePath) throws FileNotFoundException {
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
        }
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
}
