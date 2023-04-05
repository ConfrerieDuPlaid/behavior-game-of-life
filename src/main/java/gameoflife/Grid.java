package gameoflife;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

final class Grid {

    private final List<List<Cell>> cells;

    private Grid(List<List<Cell>> cells) {
        this.cells = cells;
    }

    //region String Parsing
    public static Optional<Grid> fromString(String stringGrid) {
        final var cells = Arrays
                .stream(stringGrid.split("\n"))
                .map(Grid::lineToCells)
                .toList();

        final var grid = new Grid(cells);
        return grid.isValid()
                ? Optional.of(grid)
                : Optional.empty();
    }

    private static List<Cell> lineToCells(String line) {
        return line
                .chars()
                .filter(c -> c == '.' || c == '*')
                .mapToObj(Cell::fromChar)
                .flatMap(Optional::stream)
                .toList();
    }
    //endregion

    //region Validation
    private boolean isValid() {
        if(this.haveNoRows() || this.haveNoColumns()) return false;

        return this.cells
                .stream()
                .allMatch(line -> line.size() == this.firstLineSize());
    }

    private int firstLineSize() {
        try { return this.cells.get(0).size(); }
        catch (IndexOutOfBoundsException e) { return 0; }
    }

    private boolean haveNoRows() {
        return this.cells.isEmpty();
    }

    private boolean haveNoColumns() {
        try { return this.cells.get(0).isEmpty(); }
        catch (IndexOutOfBoundsException e) { return true; }
    }
    //endregion


    public Grid next() {
        return new Grid(List.of(
                List.of(Cell.dead(), Cell.dead(), Cell.dead()),
                List.of(Cell.dead(), Cell.dead(), Cell.dead()),
                List.of(Cell.dead(), Cell.dead(), Cell.dead())
        ));
    }

    //region Equals & Hash
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
    //endregion

    //region ToString
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
    //endregion
}
