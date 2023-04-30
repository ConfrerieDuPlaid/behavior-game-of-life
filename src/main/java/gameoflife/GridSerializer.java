package gameoflife;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface GridSerializer {
    public static String serialize(Grid grid) {
        return IntStream.range(0, grid.height)
                .mapToObj(grid::line)
                .map(GridSerializer::cellsToListOfString)
                .map(line -> String.join(" ", line))
                .collect(Collectors.joining("\n"));
    }


    private static List<String> cellsToListOfString(Map<Position, Cell> cells) {
        return cells.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(p -> p.getKey().x()))
                .map(Map.Entry::getValue)
                .map(GridSerializer::cellToString)
                .toList();
    }

    public static String cellToString(Cell cell) {
        return cell.isAlive()
                ? "*"
                : ".";
    }
}
