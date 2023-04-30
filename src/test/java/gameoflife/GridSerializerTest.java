package gameoflife;

import org.junit.jupiter.api.Test;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;
import static gameoflife.Persona.deadGrid;
import static gameoflife.Persona.liveGrid;
import static gameoflife.Position.at;
import static org.junit.jupiter.api.Assertions.*;

class GridSerializerTest {

    @Test
    void liveCellShouldBeSerializedToStar() {
        assertEquals("*", GridSerializer.cellToString(alive));
    }

    @Test
    void deadCellShouldBeSerializedToDot() {
        assertEquals(".", GridSerializer.cellToString(dead));
    }

    @Test
    void serializeGridOfDeadCellsShouldBeStringOfDots() {
        assertEquals(
                """
                        . . . . . .
                        . . . . . .
                        . . . . . .
                        . . . . . .""",
                GridSerializer.serialize(deadGrid)
        );
    }

    @Test
    void serializeGridOfDeadCellsShouldBeStringOfStars() {
        assertEquals(
                """
                        * * * * * *
                        * * * * * *
                        * * * * * *
                        * * * * * *""",
                GridSerializer.serialize(liveGrid)
        );
    }

    @Test
    void serializeGridOfDeadCellsWithOneCellAliveShouldBeStringOfDotsWithOneStar() {
        final var gridOfDeadCells = Grid.ofDeadCells(3,3).get();
        assertEquals(
                """
                        . . .
                        . * .
                        . . .""",
                GridSerializer.serialize(gridOfDeadCells.withCellAt(alive, at(1,1)))
        );
    }

    @Test
    void serializeGridOfLiveCellsWithOneDeadCellShouldBeStringOfStarsWithOneDot() {
        final var gridOfLiveCells = Grid.ofLiveCells(3,3).get();
        assertEquals(
                """
                        * * *
                        * . *
                        * * *""",
                GridSerializer.serialize(gridOfLiveCells.withCellAt(dead, at(1,1)))
        );
    }


}