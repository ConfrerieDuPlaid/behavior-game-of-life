package gameoflife;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Grid {

    private final Map<Position, Cell> cells;

    private Grid(Map<Position, Cell> cells) {
        this.cells = new HashMap<>(cells);
    }

    public static Optional<Grid> of(Map<Position, Cell> cells) {
        final var grid = new Grid(cells);
        return grid.isValid()
                ? Optional.of(grid)
                : Optional.empty();
    }

    public Integer width() {
        return this.getLineSize(0);
    }

    public Integer height() {
        return this.getFirstColumn().size();
    }

    public Map<Position, Cell> cells() {
        return Collections.unmodifiableMap(this.cells);
    }

    //region Validation
    private boolean isValid() {
        if(this.cells.isEmpty()) return false;

        final int firstLineSize = this.getLine(0).entrySet().size();
        return this.intStreamOnRowIndex()
                .allMatch(y -> getLineSize(y) == firstLineSize);
    }

    private int getLineSize(int y) {
        return this.getLine(y).entrySet().size();
    }

    private Map<Position, Cell> getLine(int row) {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().y() == row)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Position, Cell> getFirstColumn() {
        return this.cells.entrySet().stream()
                .filter(entry -> entry.getKey().x() == 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    //endregion


    //region Generation
    public Grid next() {
        final var newMap = new HashMap<Position, Cell>();
        this.getAllPositions().forEach(position -> newMap.put(position, this.nextCellAt(position)));
        return Grid.of(newMap).get();
    }

    private Cell nextCellAt(Position position) {
        final var numberOfNeighbours = this.liveNeighboursAround(position);
        return this.cellAt(position).get().isAlive()
                ? this.nextLiveCellAt(numberOfNeighbours)
                : this.nextDeadCellAt(numberOfNeighbours);
    }

    private Cell nextLiveCellAt(Integer numberOfNeighbours) {
        return switch (numberOfNeighbours) {
            case 2, 3 -> Cell.alive;
            default -> Cell.dead;
        };
    }

    private Cell nextDeadCellAt(Integer numberOfNeighbours) {
        return numberOfNeighbours == 3
                ? Cell.alive
                : Cell.dead;
    }

    private Integer liveNeighboursAround(Position position) {
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

    private List<Position> getAllPositions() {
        return this.cells.keySet().stream().toList();
    }
    //endregion


    private IntStream intStreamOnRowIndex() {
        return IntStream.range(0, this.height());
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
        return this.intStreamOnRowIndex()
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
