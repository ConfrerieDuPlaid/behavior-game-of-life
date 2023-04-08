package gameoflife;

import java.util.Objects;
import java.util.Optional;

final class Cell {
    public boolean isAlive() {
        return this.state == State.ALIVE;
    }

    private enum State {ALIVE, DEAD}
    private final State state;

     private Cell(State state) {
         this.state = state;
     }

    public static final Cell alive = new Cell(State.ALIVE);
    public static final Cell dead = new Cell(State.DEAD);

    public static Optional<Cell> fromChar(int c) {
         return switch (c) {
             case '.' -> Optional.of(dead);
             case '*' -> Optional.of(alive);
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
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
    //</editor-fold>
}
