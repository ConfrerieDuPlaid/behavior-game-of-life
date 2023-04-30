package gameoflife;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;
import static gameoflife.Position.at;
import static org.junit.jupiter.api.Assertions.*;

class GridParserTest {

    @Test
    void shouldParseDotToDeadCell() {
        assertEquals(dead, GridParser.cellFromChar('.').get());
    }

    @Test
    void shouldParseStarToLiveCell() {
        assertEquals(alive, GridParser.cellFromChar('*').get());
    }

    @Test
    void shouldNotParseOtherCharsThanDotAndSpaceToCell() {
        assertTrue(GridParser.cellFromChar('@').isEmpty());
    }

    @Test
    void shouldParseGridOfOneDeadCell() {
        assertEquals(
                Grid.of(Map.of(at(0,0), dead)),
                GridParser.fromString(".")
        );
    }

    @Test
    void shouldParseGridOfTwoDeadCells() {
        assertEquals(
                Grid.of(Map.of(at(0,0), dead, at(1, 0), dead)),
                GridParser.fromString("..")
        );
    }

    @Test
    void shouldParseGridOfTwoDeadCellsOtherCharactersThatDot() {
        assertEquals(
                Grid.of(Map.of(at(0,0), dead, at(1, 0), dead)),
                GridParser.fromString(". @I9?.")
        );
    }

    @Test
    void shouldParseGridOfDeadAndLiveCells() {
        assertEquals(
                Grid.of(Map.of(
                        at(0,0), alive, at(1, 0), dead,
                        at(0,1), dead, at(1, 1), alive
                        )
                ),
                GridParser.fromString("""
                        * .
                        . *
                        """)
        );
    }

    @Test
    void shouldNotParseBadInputs() {
        assertTrue(GridParser.fromString(null).isEmpty());
        assertTrue(GridParser.fromString("").isEmpty());
        assertTrue(GridParser.fromString("badInput").isEmpty());
    }

}