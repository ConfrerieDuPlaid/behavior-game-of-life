package gameoflife;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Grid {

    private final Map<Position, Cell> cells;

    private Grid(Map<Position, Cell> cells) {
        this.cells = cells;
    }

    private Integer height() {
        return this.getFirstColumn().size();
    }

    public Map<Position, Cell> cells() {
        return this.cells;
    }

    public Grid setCellAt(Position position, Cell cell) {
        final var cells = this.cells();
        cells.put(position, cell);
        return Grid.fromMap(cells).get();
    }

    //region String Parsing
    public static Optional<Grid> fromString(String stringGrid) {
        final var lines = stringGrid.split("\n");
        final var cells = IntStream
                .range(0, lines.length)
                .mapToObj(y -> Grid.stringLineToCells(y, lines[y]))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Grid.fromMap(cells);
    }

    public static Optional<Grid> fromMap(Map<Position, Cell> cells) {
        final var grid = new Grid(cells);
        return grid.isValid()
                ? Optional.of(grid)
                : Optional.empty();
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
                .forEach(columnIndex -> map.put(Position.of(columnIndex,lineIndex).get(), cells.get(columnIndex)));

        return map;
    }
    //endregion

    //region Validation
    private boolean isValid() {
        if(this.cells.isEmpty()) return false;

        final int firstLineSize = this.getLine(0).entrySet().size();
        return this.intStreamOnRowIndex()
                .anyMatch(y -> getLineSize(y) == firstLineSize);
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
        return this.getAllPositions()
                .stream()
                .map(this::nextCell)
                .reduce((a, b) -> b).orElse(this);
    }

    private Grid nextCell(Position position) {
        return this.with(Cell.dead).at(position);
    }

    private GridWith with(Cell cell) {
        return new GridWith(this, cell);
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
