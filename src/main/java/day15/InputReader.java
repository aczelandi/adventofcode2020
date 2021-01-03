package day15;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InputReader extends BaseInputReader<List<Integer>> {

    @Override
    protected List<Integer> read(URI filePath) throws FileNotFoundException {
        List<Integer> numbers = null;
        try (var scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNext()) {
                var line = scanner.next();
                numbers = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
        }

        return numbers;
    }
}
