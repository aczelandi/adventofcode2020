package day13;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BusIdReader extends BaseInputReader<Map<Integer, Long>> {

    @Override
    protected Map<Integer, Long> read(URI filePath) throws FileNotFoundException {
        Map<Integer, Long> busIdByIndex;
        try (var scanner = new Scanner(new File(filePath))) {
            // skip first line
            scanner.nextLine();
            String[] buses = scanner.nextLine().split(",");
            busIdByIndex = IntStream.range(0, buses.length)
                    .boxed()
                    .filter(id -> !buses[id].equals("x"))
                    .collect(Collectors.toMap(k -> k, v -> Long.parseLong(buses[v])));
        }

        return busIdByIndex;
    }
}
