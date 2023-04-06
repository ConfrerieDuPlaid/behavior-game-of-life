package gameoflife;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Grid {

    private final Map<Position, Cell> cells;
    public final Integer height;
    public final Integer width;

    private Grid(Map<Position, Cell> cells) {
        this.cells = new HashMap<>(cells); // shallow copy
        this.width = this.getLine(0).size();
        this.height = this.getColumn(0).size();
    }

    public static Optional<Grid> of(Map<Position, Cell> cells) {
        final var grid = new Grid(cells);
        return grid.isValid()
                ? Optional.of(grid)
                : Optional.empty();
    }

    private boolean isValid() {
        // TODO
        if(this.cells.isEmpty()) return false;

        final int firstLineSize = this.getLine(0).size();
        return IntStream.range(0, this.height)
                .allMatch(y -> getLine(y).size() == firstLineSize);
    }

    public Map<Position, Cell> getLine(int row) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().y() == row)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Position, Cell> getColumn(int index) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().x() == index)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Stream<Cell> liveNeighboursAround(Position position) {
        return this.neighboursAround(position).filter(Cell::isAlive);
    }

    private Stream<Cell> neighboursAround(Position position) {
        return position.positionsAround()
                .map(this::cellAt)
                .filter(Optional::isPresent).map(Optional::get);
    }

    public Optional<Cell> cellAt(Position p) {
        return Optional.ofNullable(this.cells.get(p));
    }

    public Stream<Position> getAllPositions() {
        return this.cells.keySet().stream();
    }

    //region ToString & Equals & HashCode
    @Override
    public String toString() {
        return GridSerializer.serialize(this);
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
    //endregion
}
