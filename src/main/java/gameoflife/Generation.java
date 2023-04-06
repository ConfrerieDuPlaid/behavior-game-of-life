package gameoflife;

import java.util.HashMap;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;

final class Generation {
    private final Grid grid;

    public Generation(Grid grid) {
        this.grid = grid;
    }

    public Grid grid() {
        return grid;
    }

    public Generation next() {
        final var cells = new HashMap<Position, Cell>();
        this.grid.getAllPositions().forEach(position -> cells.put(position, this.nextCellAt(position)));
        return new Generation(Grid.of(cells).get());
    }

    private Cell nextCellAt(Position position) {
        final var numberOfLiveNeighbours = this.grid.liveNeighboursAround(position).toList().size();
        return this.grid.cellAt(position).get().isAlive()
                ? this.nextLiveCellAt(numberOfLiveNeighbours)
                : this.nextDeadCellAt(numberOfLiveNeighbours);
    }

    private Cell nextLiveCellAt(Integer numberOfNeighbours) {
        return switch (numberOfNeighbours) {
            case 2, 3 -> alive;
            default -> dead;
        };
    }

    private Cell nextDeadCellAt(Integer numberOfNeighbours) {
        return numberOfNeighbours == 3
                ? alive
                : dead;
    }
}
