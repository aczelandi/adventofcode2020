package day20;

import common.BaseInputReader;
import day20.model.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class InputReader extends BaseInputReader<Map<Integer, Tile>> {

    @Override
    protected Map<Integer, Tile> read(URI filePath) throws FileNotFoundException {
        var tiles = new HashMap<Integer, Tile>();
        try (var scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter("\n\\s+");
            while (scanner.hasNext()) {
                var currentTile = scanner.next().split("\n");
                var idStr = currentTile[0].replace("Tile ", "").replace(":", "");
                var id = Integer.parseInt(idStr);
                var tileSize = currentTile[1].length();
                var top = currentTile[1];
                var bottom = currentTile[currentTile.length - 1];
                var left = IntStream.range(1, currentTile.length)
                        .boxed()
                        .map(index -> Character.toString(currentTile[index].charAt(0)))
                        .reduce((s1, s2) -> s1 + s2)
                        .orElseThrow();

                var right = IntStream.range(1, currentTile.length)
                        .boxed()
                        .map(index -> Character.toString(currentTile[index].charAt(tileSize - 1)))
                        .reduce((s1, s2) -> s1 + s2)
                        .orElseThrow();

                tiles.put(id, new Tile(id, top, right, bottom, left));
            }
        }

        return tiles;
    }
}
