package day13;

import common.Pair2;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        var busIdReader = new BusIdReader();
        var busIdsByIndex = busIdReader.readInput("day13/input.txt");

        var preferredTimestampReader = new PreferredTimestampReader();
        var preferredTimestamp = preferredTimestampReader.readInput("day13/input.txt");

        var solution = new SolutionImpl(new Pair2<>(preferredTimestamp, busIdsByIndex));
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
