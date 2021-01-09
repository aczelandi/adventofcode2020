package day22;

import common.BaseInputReader;
import day22.model.Game;
import day22.model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputReader extends BaseInputReader<Game> {

    @Override
    protected Game read(URI filePath) throws FileNotFoundException {
        var players = new ArrayList<Player>();
        try (var scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter("\n\\s+");
            while (scanner.hasNext()) {
                var playerInfo = scanner.next().split("\n");
                var id = playerInfo[0];
                var cards = IntStream.range(1, playerInfo.length)
                        .boxed()
                        .map(card -> Integer.parseInt(playerInfo[card]))
                        .collect(Collectors.toCollection(LinkedList::new));

                players.add(new Player(id, cards));
            }
        }

        return new Game(players.get(0), players.get(1));
    }
}
