package gameoflife;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Grid {

    private final Map<Position, Cell> cells;
    public final Integer height;
    public final Integer width;

    private Grid(Map<Position, Cell> cells) {
        this.cells = new HashMap<>(cells);
        this.width = this.getLine(0).size();
        this.height = this.getColumn(0).size();
    }

    public static Optional<Grid> of(Map<Position, Cell> cells) {
        final var grid = new Grid(cells);
        return grid.isValid()
                ? Optional.of(grid)
                : Optional.empty();
    }

    //region Validation
    private boolean isValid() {
        if(this.cells.isEmpty()) return false;

        final int firstLineSize = this.getLine(0).entrySet().size();
        return IntStream.range(0, this.height)
                .allMatch(y -> getLine(y).size() == firstLineSize);
    }

    private Map<Position, Cell> getLine(int row) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().y() == row)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Position, Cell> getColumn(int index) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().x() == index)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    //endregion

    public Integer liveNeighboursAround(Position position) {
        return (int) position.positionsAround()
                .stream()
                .map(this::cellAt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Cell::isAlive)
                .count();
    }

    public Optional<Cell> cellAt(Position p) {
        return Optional.ofNullable(this.cells.get(p));
    }

    public List<Position> getAllPositions() {
        return this.cells.keySet().stream().toList();
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
        return IntStream.range(0, this.height)
                .mapToObj(this::getLine)
                .map(Grid::cellsToListOfString)
                .map(line -> String.join(" ", line))
                .collect(Collectors.joining("\n"));
    }


    private static List<String> cellsToListOfString(Map<Position, Cell> cells) {
        return cells.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(p -> p.getKey().x()))
                .map(Map.Entry::getValue)
                .map(Cell::toString)
                .toList();
    }
    //endregion
}
