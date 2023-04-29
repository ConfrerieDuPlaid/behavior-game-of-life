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
        final var gridOfDeadCells = Persona.gridOfDeadCells(3,3);
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
        final var gridOfLiveCells = Persona.gridOfLiveCells(3,3);
        assertEquals(
                """
                        * * *
                        * . *
                        * * *""",
                GridSerializer.serialize(gridOfLiveCells.withCellAt(dead, at(1,1)))
        );
    }


}