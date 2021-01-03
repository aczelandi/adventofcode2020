package day03;

import common.BaseInputReader;
import day03.model.Forest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InputReader extends BaseInputReader<Forest> {

    @Override
    protected Forest read(URI filePath) throws FileNotFoundException {
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
        }

        var elements = new char[rows.size()][colCount];
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < colCount; j++) {
                elements[i][j] = rows.get(i).get(j);
            }
        }

        return new Forest(elements, rows.size(), colCount);
    }
}
