package day23;

import common.BaseInputReader;
import day23.model.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

class InputReader extends BaseInputReader<Game> {

    private boolean withExtension;

    public InputReader(boolean withExtension) {
        this.withExtension = withExtension;
    }

    @Override
    protected Game read(URI filePath) throws FileNotFoundException {
        try (var scanner = new Scanner(new File(filePath))) {
            var line = scanner.nextLine();
            var cups = line.chars()
                    .mapToObj(c -> Integer.parseInt(Character.toString(c)))
                    .collect(Collectors.toCollection(LinkedList::new));

            if (withExtension) {
                for (int i = cups.size() + 1; i < 1_000_000; i++) {
                    cups.add(i);
                }
            }
            return new Game(cups);
        }
    }
}
