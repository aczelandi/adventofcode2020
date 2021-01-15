package day23.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {

    private final LinkedList<Integer> cups;

    private int currentIndex = 0;

    public Game(LinkedList<Integer> cups) {
        this.cups = cups;
    }

    public int currentCup() {
        return this.cups.get(this.currentIndex);
    }

    public int getIndexByUniqueValue(int value) {
        return IntStream.range(0, this.cups.size()).boxed().filter(index -> this.cups.get(index) == value).findFirst().orElseThrow();
    }

    public List<Integer> removeCups(int amount) {
        var removed = new ArrayList<Integer>();
        var index = getNextIndex(this.currentIndex);
        for (int i = 0; i < amount; i++) {
            removed.add(this.cups.get(index));
            index = getNextIndex(index);
        }

        this.cups.removeAll(removed);

        return removed;
    }

    public void insertAtIndex(List<Integer> nextThreeCups, int preferredDestinationIndex) {
        IntStream.range(preferredDestinationIndex + 1, preferredDestinationIndex + 1 + nextThreeCups.size())
                .boxed()
                .forEach(index -> this.cups.add(index, nextThreeCups.get(index - 1 - preferredDestinationIndex)));
    }

    public int getValueAtOffset(int offset) {
        var index = this.currentIndex;
        for (int i = 0; i < offset; i++) {
            index = getNextIndex(index);
        }

        return this.cups.get(index);
    }

    public int minCup() {
        return this.cups.stream().mapToInt(x -> x).min().orElseThrow();
    }

    public int maxCup() {
        return this.cups.stream().mapToInt(x -> x).max().orElseThrow();
    }

    public void setIndex(int nextIndex) {
        this.currentIndex = nextIndex;
    }

    public String getScore() {
        var index = this.getIndexByUniqueValue(1);
        var builder = new StringBuilder();
        for (int i = 0; i < this.cups.size() - 1; i++) {
            var nextIndex = this.getNextIndex(index);
            builder.append(this.cups.get(nextIndex));
            index = nextIndex;
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Game{" +
                "cups=" + cups +
                ", currentIndex=" + currentIndex +
                '}';
    }

    private int getNextIndex(int index) {
        return index == this.cups.size() - 1 ? 0 : index + 1;
    }
}
