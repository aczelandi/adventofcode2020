package day18;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class InputReader<T> extends BaseInputReader<List<T>> {

    private final Function<String, T> parser;

    public InputReader(Function<String, T>  parser) {
        this.parser = parser;
    }

    @Override
    protected List<T> read(URI filePath) throws FileNotFoundException {
        var expressions = new ArrayList<T>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var expression = scanner.nextLine();
                expressions.add(this.parser.apply(expression));
            }
        }

        return expressions;
    }
}
