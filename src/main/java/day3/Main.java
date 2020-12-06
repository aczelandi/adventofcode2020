package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var resource = program.getClass().getClassLoader().getResource("day3/input.txt");
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        }
        var inputs = program.readInputs(resource.toURI());
        System.out.println(inputs);
        var treesOnPath1 = program.computeTreesOnPath(inputs, 3, 1);
        System.out.println(treesOnPath1);

        var treesOnPath2 = program.computeTreesOnPath(inputs, 1, 1);
        System.out.println(treesOnPath2);
        var treesOnPath3 = program.computeTreesOnPath(inputs, 5, 1);
        System.out.println(treesOnPath3);
        var treesOnPath4 = program.computeTreesOnPath(inputs, 7, 1);
        System.out.println(treesOnPath4);
        var treesOnPath5 = program.computeTreesOnPath(inputs, 1, 2);
        System.out.println(treesOnPath5);

        System.out.println(treesOnPath1 * treesOnPath2 * treesOnPath3 * treesOnPath4 * treesOnPath5);
    }

    private Forest readInputs(URI filePath) {
        var rows = new ArrayList<List<Character>>();
        var colCount = 0;
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var line = sc.nextLine();
                if (colCount == 0) {
                    colCount = line.length();
                }
                var chars = line.chars().boxed().map(c -> (char) c.intValue()).collect(Collectors.toList());
                rows.add(chars);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        var elements = new char[rows.size()][colCount];
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < colCount; j++) {
                elements[i][j] = rows.get(i).get(j);
            }
        }

        return new Forest(elements, rows.size(), colCount);
    }

    private long computeTreesOnPath(Forest forest, int rightMoves, int downMoves) {
        var elements = forest.elements;
        var rowLength = forest.rowLength;
        var colLength = forest.colLength;
        var currentColIndex = 0;
        var trees = 0;

        for (int i = 0; i < rowLength; i += downMoves) {
            if (elements[i][currentColIndex] == '#') {
                trees++;
            }

            currentColIndex = (currentColIndex + rightMoves) % colLength;
        }

        return trees;
    }

    private record Forest(char[][]elements, int rowLength, int colLength) {

        @Override
        public String toString() {
            var str = new StringBuilder();
            for (int i = 0; i < rowLength; i++) {
                for (int j = 0; j < colLength; j++) {
                    str.append(elements[i][j]);
                }
                str.append("\n");
            }

            str.append("Rows: ").append(rowLength).append("\n");
            str.append("Columns: ").append(colLength).append("\n");
            return str.toString();
        }
    }
}
