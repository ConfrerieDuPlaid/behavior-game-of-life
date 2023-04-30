package gameoflife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void aliveCellShouldBeAlive() {
        assertTrue(Cell.alive.isAlive());
    }

    @Test
    void deadCellShouldNotBeAlive() {
        assertFalse(Cell.dead.isAlive());
    }

    @Test
    void cellsOnSameStateShouldBeEqual() {
        assertTrue(Cell.alive.equals(Cell.alive));
    }
}