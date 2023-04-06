package gameoflife;

import java.util.Objects;
import java.util.Optional;

final class Cell {
    private enum State {ALIVE, DEAD}
    private final State state;

     private Cell(State state) {
         this.state = state;
     }

    public static final Cell alive = new Cell(State.ALIVE);
    public static final Cell dead = new Cell(State.DEAD);

    public static Optional<Cell> fromChar(int c) {
         return switch (c) {
             case '.' -> Optional.of(Cell.dead);
             case '*' -> Optional.of(Cell.alive);
             default -> Optional.empty();
         };
    }

    @Override
    public String toString() {
        return this.state == State.ALIVE
                ? "*"
                : ".";
    }

    //<editor-fold desc="Equals & Hash">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return state == cell.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
    //</editor-fold>
}
