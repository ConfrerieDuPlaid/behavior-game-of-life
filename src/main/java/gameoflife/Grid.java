package gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class Grid {

    private final List<List<Cell>> cells;

    private Grid(List<List<Cell>> cells) {
        this.cells = cells;
    }

    public static Grid fromString(String grid) {
        final var cells = Arrays
                .stream(grid.split("\n"))
                .map(Grid::lineToCells)
                .toList();
        return new Grid(cells);
    }

    private static List<Cell> lineToCells(String line) {
       return line
               .chars()
               .filter(c -> c == '.' || c == '*')
               .mapToObj(Cell::fromChar)
               .toList();
    }

    public Grid next() {
        return new Grid(List.of(
                List.of(Cell.dead(), Cell.dead(), Cell.dead()),
                List.of(Cell.dead(), Cell.dead(), Cell.dead()),
                List.of(Cell.dead(), Cell.dead(), Cell.dead())
        ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return cells.equals(grid.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public String toString() {
        return this.cells.stream()
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
