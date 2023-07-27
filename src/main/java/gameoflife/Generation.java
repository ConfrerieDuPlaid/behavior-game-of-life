package gameoflife;

import java.util.Optional;

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
        final var nextGrid = this.grid.getAllPositions()
                .reduce(
                        this.grid,
                        (Grid acc, Position next) -> this.gridWithCellInNextStateAt(acc, next).orElse(acc),
                        (oldGrid, gridWithNextCell) -> gridWithNextCell
                );
        return new Generation(nextGrid);
    }

    private Optional<Grid> gridWithCellInNextStateAt(Grid grid, Position position) {
        final var cellInNextState = cellInNextStateAt(position);
        return cellInNextState.map(cell -> grid.withCellAt(cell, position));
    }

    private Optional<Cell> cellInNextStateAt(Position position) {
        final var cell = this.grid.cellAt(position);
        if(cell.isEmpty()) return Optional.empty();

        final var numberOfLiveNeighbours = this.grid.liveNeighboursAround(position).toList().size();
        final var nextCell = cell.get().isAlive()
                ? this.nextLiveCellAt(numberOfLiveNeighbours)
                : this.nextDeadCellAt(numberOfLiveNeighbours);
        return Optional.of(nextCell);
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
