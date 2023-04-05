package gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class Grid {

    private final Cell[][] cells;

    private Grid(Cell[][] cells) {
        this.cells = cells;
    }

    public static Grid fromString(String grid) {
        final var cells = Arrays
                .stream(grid.split("\n"))
                .map(Grid::lineToCells)
                .toArray(Cell[][]::new);
        return new Grid(cells);
    }

    private static Cell[] lineToCells(String line) {
       return line
               .chars()
               .filter(c -> c == '.' || c == '*')
               .mapToObj(Cell::fromChar)
               .toArray(Cell[]::new);
    }

    public Grid next() {
        final Cell[][] cells = {
                {Cell.dead(), Cell.dead(), Cell.dead()},
                {Cell.dead(), Cell.dead(), Cell.dead()},
                {Cell.dead(), Cell.dead(), Cell.dead()}
        };
        return new Grid(cells);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Arrays.deepEquals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
    }

    @Override
    public String toString() {
        return Arrays.stream(this.cells)
                .map(Arrays::asList)
                .map(Grid::cellsToListOfString)
                .map(line -> String.join(" ", line))
                .collect(Collectors.joining("\n"));
    }

    private static List<String> cellsToListOfString(List<Cell> cells) {
        return cells
                .stream()
                .map(Cell::toString)
                .toList();
    }
}
