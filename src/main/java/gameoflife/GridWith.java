package gameoflife;

public class GridWith {
    private final Grid grid;
    private final Cell cell;

    public GridWith(Grid grid, Cell cell) {
        this.grid = grid;
        this.cell = cell;
    }

    public Grid at(Position position) {
        return this.grid.setCellAt(position, this.cell);
    }
}
