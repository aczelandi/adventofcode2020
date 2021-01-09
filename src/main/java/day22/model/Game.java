package day22.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    private final Player player1;

    private final Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    private final Set<Round> rounds = new HashSet<>();

    public Player playCombat() {
        while (!finished(this.player1, this.player2)) {
            var topCardPlayer1 = this.player1.cards().pop();
            var topCardPlayer2 = this.player2.cards().pop();

            if (topCardPlayer1 > topCardPlayer2) {
                this.player1.insertCards(topCardPlayer1, topCardPlayer2);
            } else {
                this.player2.insertCards(topCardPlayer2, topCardPlayer1);
            }
        }

        return getWinner(this.player1, this.player2);
    }

    public Player playRecursiveCombat() {
        return playRecursive(this.player1, this.player2, 1, new AtomicInteger(2));
    }

    public Player playRecursive(Player player1, Player player2, int gameId, AtomicInteger nextAvailableId) {
        if (finished(player1, player2)) {
            return getWinner(player1, player2);
        }

        var currentRound = new Round(gameId, player1.cloneCards(), player2.cloneCards());
        if (this.rounds.contains(currentRound)) {
            throw new InfiniteGameException();
        }

        this.rounds.add(currentRound);

        var topCardPlayer1 = player1.cards().pop();
        var topCardPlayer2 = player2.cards().pop();
        var isRecursiveCombat = player1.cards().size() >= topCardPlayer1 && player2.cards().size() >= topCardPlayer2;

        if (isRecursiveCombat) {
            var subGameId = nextAvailableId.getAndIncrement();

            Player combatWinner;
            try {
                combatWinner = playRecursive(
                        new Player(player1.id(), new LinkedList<>(player1.cards().subList(0, topCardPlayer1))),
                        new Player(player2.id(), new LinkedList<>(player2.cards().subList(0, topCardPlayer2))),
                        subGameId, nextAvailableId);
            } catch (InfiniteGameException e) {
                combatWinner = player1;
            }

            if (combatWinner.id().equals(player1.id())) {
                player1.insertCards(topCardPlayer1, topCardPlayer2);
            } else {
                player2.insertCards(topCardPlayer2, topCardPlayer1);
            }
        } else {
            if (topCardPlayer1 > topCardPlayer2) {
                player1.insertCards(topCardPlayer1, topCardPlayer2);
            } else {
                player2.insertCards(topCardPlayer2, topCardPlayer1);
            }
        }
        return playRecursive(player1, player2, gameId, nextAvailableId);
    }

    private Player getWinner(Player player1, Player player2) {
        return player1.finishedCards() ? player2 : player1;
    }

    private boolean finished(Player player1, Player player2) {
        return player1.finishedCards() || player2.finishedCards();
    }

    @Override
    public String toString() {
        return "Game{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", rounds=" + rounds +
                '}';
    }

    private static class InfiniteGameException extends RuntimeException {
    }
}
