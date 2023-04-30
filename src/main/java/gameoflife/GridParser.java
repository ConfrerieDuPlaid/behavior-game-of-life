package gameoflife;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;
import static java.util.stream.Collectors.toMap;

interface GridParser {
    static Optional<Grid> fromString(String stringGrid) {
        if (stringGrid == null) return Optional.empty();
        final var lines = stringGrid.split("\n");
        final var cells = IntStream
                .range(0, lines.length)
                .mapToObj(y -> GridParser.lineToRow(y, lines[y]))
                .flatMap(lineOfCells -> lineOfCells.entrySet().stream())
                .collect(toMap(Entry::getKey, Entry::getValue));

        return Grid.of(cells);
    }

    private static Map<Position, Cell> lineToRow(int rowIndex, String line) {
        final var cells = line
                .chars()
                .filter(c -> c == '.' || c == '*')
                .mapToObj(GridParser::cellFromChar)
                .flatMap(Optional::stream)
                .toList();

        final var map = new HashMap<Position, Cell>();
        IntStream.range(0, cells.size())
                .mapToObj(columnIndex -> Position.at(columnIndex,rowIndex))
                .forEach(position -> map.put(position, cells.get(position.x())));
        return map;
    }

    public static Optional<Cell> cellFromChar(int c) {
        return switch (c) {
            case '.' -> Optional.of(dead);
            case '*' -> Optional.of(alive);
            default -> Optional.empty();
        };
    }
}
