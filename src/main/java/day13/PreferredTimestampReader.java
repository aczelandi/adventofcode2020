package day13;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

class PreferredTimestampReader extends BaseInputReader<Long> {

    @Override
    protected Long read(URI filePath) throws FileNotFoundException {
        var preferredTimestamp = 0L;
        try (var scanner = new Scanner(new File(filePath))) {
            preferredTimestamp = scanner.nextLong();
        }

        return preferredTimestamp;
    }
}
