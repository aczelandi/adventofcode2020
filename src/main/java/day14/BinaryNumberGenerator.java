package day14;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BinaryNumberGenerator {

    public static List<String> generate(long length) {
        if (length == 0) {
            return Collections.emptyList();
        }

        return IntStream.range(0, (int) Math.pow(2, length))
                .boxed()
                .map(i -> {
                    var stringValue = Integer.toBinaryString(i);
                    return String.format("%" + length + "s", stringValue).replace(' ', '0');
                })
                .collect(Collectors.toList());
    }
}
