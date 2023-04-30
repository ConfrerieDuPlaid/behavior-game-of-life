package gameoflife;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import static gameoflife.Position.at;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

final class Grid {

    protected final Map<Position, Cell> cells;
    public final Integer height;
    public final Integer width;

    // private constructor
    private Grid(Map<Position, Cell> cells) {
        this.cells = new HashMap<>(cells); // shallow copy
        this.width = this.line(0).size();
        this.height = this.column(0).size();
    }

    // static factory method
    public static Optional<Grid> ofDeadCells(int rows, int columns) {
        if(rows < 1 || columns < 1) return Optional.empty();

        final var cells = new HashMap<Position, Cell>();
        range(0, rows)
                .forEach(row -> range(0, columns)
                        .forEach(column -> cells.put(at(column, row), Cell.dead)));
        return Optional.of(new Grid(cells));
    }

    public static Optional<Grid> ofLiveCells(int rows, int columns) {
        if(rows < 1 || columns < 1) return Optional.empty();

        final var cells = new HashMap<Position, Cell>();
        range(0, rows)
                .forEach(row -> range(0, columns)
                        .forEach(column -> cells.put(at(column, row), Cell.alive)));
        return Optional.of(new Grid(cells));
    }

    public static Optional<Grid> of(Map<Position, Cell> cells) {
        if(!Grid.cellsAreValid(cells)) return Optional.empty();
        return Optional.of(new Grid(cells));
    }

    private static boolean cellsAreValid(Map<Position, Cell> cells) {
        if(cells == null || cells.isEmpty()) return false;

        final var noNullPosition = cells.keySet().stream().allMatch(Objects::nonNull);
        final var noNullCell = cells.values().stream().allMatch(Objects::nonNull);

        return noNullPosition && noNullCell;
    }

    public Map<Position, Cell> line(int row) {
        return this.cells.entrySet().stream()
                .filter(cell -> cell.getKey().y() == row)
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    private Map<Position, Cell> column(int index) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().x() == index)
                .collect(toMap(Entry::getKey, Entry::getValue));
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

    public Grid withCellAt(Cell cell, Position position) {
        final var cloned = new Grid(this.cells);
        cloned.cells.put(position, cell);
        return cloned;
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
