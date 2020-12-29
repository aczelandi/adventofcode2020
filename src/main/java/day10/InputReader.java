package day10;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputReader extends BaseInputReader<List<Integer>> {

    @Override
    protected List<Integer> read(URI filePath) throws FileNotFoundException {
        var numbers = new ArrayList<Integer>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                numbers.add(Integer.parseInt(scanner.nextLine()));
            }
        }

        return numbers;
    }
}
