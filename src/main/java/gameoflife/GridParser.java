package gameoflife;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class GridParser {
    private GridParser() {
        throw new AssertionError(); // Interrupt instantiation
    }

    public static Optional<Grid> fromString(String stringGrid) {
        final var lines = stringGrid.split("\n");
        final var cells = IntStream
                .range(0, lines.length)
                .mapToObj(y -> GridParser.stringLineToCells(y, lines[y]))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Grid.of(cells);
    }

    private static Map<Position, Cell> stringLineToCells(int lineIndex, String line) {
        final var cells = line
                .chars()
                .filter(c -> c == '.' || c == '*')
                .mapToObj(Cell::fromChar)
                .flatMap(Optional::stream)
                .toList();

        final var map = new HashMap<Position, Cell>();
        IntStream.range(0, cells.size())
                .forEach(columnIndex -> map.put(Position.of(columnIndex,lineIndex), cells.get(columnIndex)));

        return map;
    }
}
