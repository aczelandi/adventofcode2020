package day22.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public record Player(String id, LinkedList<Integer>cards) {

    public boolean finishedCards() {
        return cards.isEmpty();
    }

    public void insertCards(int... winnerCards) {
        Arrays.stream(winnerCards).forEach(cards::addLast);
    }

    public long score() {
        var length = cards.size();
        return IntStream.range(0, length)
                .boxed()
                .mapToLong(i -> cards.get(i) * (length - i))
                .sum();
    }

    public List<Integer> cloneCards() {
        return new ArrayList<>(cards);
    }
}
