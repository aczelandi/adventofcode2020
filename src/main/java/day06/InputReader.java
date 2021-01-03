package day06;

import common.BaseInputReader;
import common.Pair2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputReader extends BaseInputReader<List<Pair2<Integer, String>>> {

    @Override
    protected List<Pair2<Integer, String>> read(URI filePath) throws FileNotFoundException {
        var personGroups = new ArrayList<Pair2<Integer, String>>();
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var group = sc.next();
                var personsInGroup = group.split("\n").length;
                personGroups.add(new Pair2<>(personsInGroup, group.replace("\n", "")));
            }
        }

        return personGroups;
    }
}
