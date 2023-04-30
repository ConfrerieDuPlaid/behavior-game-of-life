package gameoflife;

import static gameoflife.Cell.alive;

final class Persona {

    public final static Grid deadGrid = Grid.ofDeadCells(4, 6).get();
    public final static Grid liveGrid = Grid.ofLiveCells(4, 6).get();
}
