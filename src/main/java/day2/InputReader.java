package day2;

import common.BaseInputReader;
import day2.model.Input;
import day2.model.Policy;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputReader extends BaseInputReader<List<Input>> {
    @Override
    protected List<Input> read(URI filePath) throws FileNotFoundException {
        var items = new ArrayList<Input>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var line = sc.nextLine();
                var item = line.split(":");
                var policy = parsePolicy(item[0]);
                var password = item[1].trim();
                items.add(new Input(policy, password));
            }
        }

        return items;
    }

    private Policy parsePolicy(String policyStr) {
        var components = policyStr.split(" ", 2);
        var lowAndHigh = components[0].split("-");
        var index1 = Integer.parseInt(lowAndHigh[0]);
        var index2 = Integer.parseInt(lowAndHigh[1]);
        return new Policy(index1, index2, components[1].toCharArray()[0]);
    }
}
