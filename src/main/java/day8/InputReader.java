package day8;

import common.BaseInputReader;
import day8.model.Accumulation;
import day8.model.Jump;
import day8.model.NoOp;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader extends BaseInputReader<List<Expression>> {

    @Override
    protected List<Expression> read(URI filePath) throws FileNotFoundException {
        var expressions = new ArrayList<Expression>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var group = sc.nextLine();
                var components = group.split(" ");
                var operation = components[0];
                var argument = components[1];
                var sign = argument.charAt(0);
                var number = Integer.parseInt(argument.substring(1));
                var offset = (sign == '+') ? number : number * -1;
                Expression expression = switch (operation) {
                    case "acc" -> new Accumulation(offset);
                    case "jmp" -> new Jump(offset);
                    case "nop" -> new NoOp(offset);
                    default -> throw new IllegalArgumentException("UnexpectedException");
                };

                expressions.add(expression);
            }
        }

        return expressions;
    }
}
