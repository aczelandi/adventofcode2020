package day09;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputReader extends BaseInputReader<List<Long>> {

    @Override
    protected List<Long> read(URI filePath) throws FileNotFoundException {
        var numbers = new ArrayList<Long>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                numbers.add(sc.nextLong());
            }
        }

        return numbers;
    }
}
