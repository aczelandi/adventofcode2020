package day17;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

class GridSizeReader extends BaseInputReader<Integer> {

    @Override
    protected Integer read(URI filePath) throws FileNotFoundException {
        try (var scanner = new Scanner(new File(filePath))) {
            return scanner.nextLine().length();
        }
    }
}
