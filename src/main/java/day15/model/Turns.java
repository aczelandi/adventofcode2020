package day15.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Turns {

    private Integer previousTurn;

    private Integer lastTurn;

    public Turns(List<Integer> indexes) {
        var indexesInOrder = indexes.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        if (indexesInOrder.size() > 0) {
            this.lastTurn = indexes.get(0);
        }
        if (indexesInOrder.size() > 1) {
            this.previousTurn = indexes.get(1);
        }
    }

    public void addNewTurn(Integer newTurn) {
        this.previousTurn = lastTurn;
        this.lastTurn = newTurn;
    }

    public Integer getPreviousTurn() {
        return previousTurn;
    }

    public Integer getLastTurn() {
        return lastTurn;
    }

    @Override
    public String toString() {
        return "Turns{" +
                "previousTurn=" + previousTurn +
                ", lastTurn=" + lastTurn +
                '}';
    }
}
