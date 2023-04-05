package gameoflife;

import java.util.Objects;

final class Cell {
    private enum State {ALIVE, DEAD};

    private final State state;

     private Cell(State state) {
         this.state = state;
     }

     public static Cell alive() {
         return new Cell(State.ALIVE);
     }

     public static Cell dead() {
         return new Cell(State.DEAD);
     }

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
}
