package gameoflife;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface GridParser {
    static Optional<Grid> fromString(String stringGrid) {
        if (stringGrid == null) return Optional.empty();
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
                .mapToObj(columnIndex -> Position.of(columnIndex,lineIndex))
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(position -> map.put(position, cells.get(position.x())));
        return map;
    }
}
