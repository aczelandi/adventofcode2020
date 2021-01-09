package day22.model;

import java.util.List;

public record Round(int gameId, List<Integer>player1Cards, List<Integer>player2Cards) {
}
