package gameoflife;

import java.util.HashMap;
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
        //this.grid.getAllPositions().forEach(position -> cells.put(position, this.nextCellAt(position)));
        final var nextGrid = this.grid.getAllPositions()
                .reduce(
                        this.grid,
                        (Grid acc, Position next) -> acc.withCellAt(nextCellAt(next), next),
                        (old, next) -> next
                );
        return new Generation(nextGrid);
    }

    private Cell nextCellAt(Position position) {
        // TODO cell not present at position
        //final var cell = this.grid.cellAt(position);
        //if(cell.isEmpty()) return Optional.empty();
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
