package day19;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputMessagesReader extends BaseInputReader<List<String>> {

    @Override
    protected List<String> read(URI filePath) throws FileNotFoundException {
        var data = new ArrayList<String>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                if (scanner.nextLine().isEmpty()) {
                    break;
                }
            }

            while (scanner.hasNext()) {
                data.add(scanner.nextLine());
            }
        }

        return data;
    }
}
