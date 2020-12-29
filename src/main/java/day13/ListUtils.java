package day13;

import java.util.List;
import java.util.stream.IntStream;

public class ListUtils {

    public static int findIndexInSortedList(List<Long> list, long number) {
        return IntStream.range(0, list.size() - 1)
                .boxed()
                .filter(index -> list.get(index) <= number && list.get(index + 1) >= number)
                .map(index -> index + 1)
                .findFirst()
                .orElseThrow();
    }
}
