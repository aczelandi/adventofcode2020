package day14;

import common.BaseInputReader;
import day14.model.MaskingOperation;
import day14.model.MemoryOperation;
import day14.model.Operation;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class InputReader extends BaseInputReader<List<Operation>> {

    private static final Pattern memoryOperationPattern = Pattern.compile("mem\\[([0-9]*)\\]=([0-9]*)");

    @Override
    protected List<Operation> read(URI filePath) throws FileNotFoundException {
        var operations = new ArrayList<Operation>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine().replaceAll("\\s+", "");
                if (line.startsWith("mask")) {
                    var operation = new MaskingOperation(line.split("=")[1]);
                    operations.add(operation);
                } else if (line.startsWith("mem")) {
                    var matcher = memoryOperationPattern.matcher(line);
                    while (matcher.find()) {
                        var memoryLocation = matcher.group(1);
                        var value = matcher.group(2);
                        var operation = new MemoryOperation(new BigInteger(memoryLocation), new BigInteger(value));
                        operations.add(operation);
                    }
                } else {
                    throw new IllegalStateException("Unexpected operation " + line);
                }
            }
        }

        return operations;
    }
}
