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
}
