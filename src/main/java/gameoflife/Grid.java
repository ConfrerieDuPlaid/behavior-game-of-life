package gameoflife;

import java.util.Arrays;

final class Grid {

    private final Cell[][] cells;

    private Grid(Cell[][] cells) {
        this.cells = cells;
    }

    public static Grid fromString(String grid) {
        Cell[][] cells = {
                {Cell.dead(), Cell.dead(), Cell.dead()},
                {Cell.dead(), Cell.dead(), Cell.dead()},
                {Cell.dead(), Cell.dead(), Cell.dead()}
        };
        return new Grid(cells);
    }

    public Grid next() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Arrays.deepEquals(cells, grid.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
    }
}
