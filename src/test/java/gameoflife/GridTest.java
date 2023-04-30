package gameoflife;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;
import static gameoflife.Position.at;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GridTest {

    @Test
    void shouldCreateGridOfLiveCells() {
        final var deadGrid = Grid.ofLiveCells(3,3).get();
        assertTrue(deadGrid.getAllPositions().allMatch(p -> deadGrid.cellAt(p).get().isAlive()));
    }
    @Test
    void shouldCreateGridOfDeadCells() {
        final var deadGrid = Grid.ofDeadCells(3,3).get();
        assertFalse(deadGrid.getAllPositions().allMatch(p -> deadGrid.cellAt(p).get().isAlive()));
    }

    @Test
    void shouldNotCreateEmptyGrid() {
        Assertions.assertThat(Grid.ofDeadCells(0,0)).isEmpty();
    }

    @Test
    void shouldCreateGridOfOneCell() {
        Assertions.assertThat(Grid.ofDeadCells(1,1)).isNotEmpty();
    }

    @Test
    void shouldCreateGridOfDeadCellsFromMap() {
        Assertions.assertThat(Grid.of(
                Map.of(
                        at(0,0), dead, at(1,0), dead, at(2,0), dead,
                        at(0,1), dead, at(1,1), dead, at(2,1), dead,
                        at(0,2), dead, at(1,2), dead, at(2,2), dead
                )
        )).isNotEmpty();
    }

    @Test
    void shouldNotCreateGridWithNullMap() {
        Assertions.assertThat(Grid.of(null)).isEmpty();
    }

    @Test
    void shouldNotCreateGridWithNullCell() {
        final var cells = new HashMap<Position, Cell>();
        cells.put(at(0,0), null);
        Assertions.assertThat(Grid.of(cells)).isEmpty();
    }

    @Test
    void shouldNotCreateGridWithNullPosition() {
        final var cells = new HashMap<Position, Cell>();
        cells.put(null, alive);
        Assertions.assertThat(Grid.of(cells)).isEmpty();
    }

    @Test
    void shouldCreateCopyOfGridWithCellAtPosition() {
        final var grid = Persona.deadGrid;
        final var withCellAt = grid.withCellAt(alive, at(1,3));

        Assertions.assertThat(withCellAt.cells.get(at(1,3))).isEqualTo(alive);
        Assertions
                .assertThat(System.identityHashCode(grid))
                .isNotEqualTo(System.identityHashCode(withCellAt));
    }

    @Test
    void shouldGetCellAtPosition() {
        final var grid = Grid.ofDeadCells(4,6).get();
        grid.cells.put(at(1,3), alive);
        Assertions.assertThat(grid.cellAt(at(1,3))).contains(alive);
    }

    @Test
    void shouldGetHeightLiveNeighboursAround() {
        Assertions
                .assertThat(Persona.liveGrid.liveNeighboursAround(at(1,1)))
                .containsExactly(alive, alive, alive, alive, alive, alive, alive, alive);
    }

    @Test
    void shouldGetHeightLiveNeighboursAroundEdge() {
        Assertions
                .assertThat(Persona.liveGrid.liveNeighboursAround(at(0,0)))
                .containsExactly(alive, alive, alive);
    }

    @Test
    void shouldToStringWithSerializer() {
        final var grid = Persona.liveGrid;
        Assertions.assertThat(grid.toString()).isEqualTo(GridSerializer.serialize(grid));
    }

    @Test
    void gridsWithSameDispositionOfCellsShouldBeEqual() {
        final var grid1 = Grid.of(Map.of(
                at(0,0), alive, at(1,0), dead, at(2,0), dead,
                at(0,1), alive, at(1,1), dead, at(2,1), dead,
                at(0,2), dead, at(1,2), dead, at(2,2), alive
        ));

        final var grid2 = Grid.of(Map.of(
                at(0,0), alive, at(1,0), dead, at(2,0), dead,
                at(0,1), alive, at(1,1), dead, at(2,1), dead,
                at(0,2), dead, at(1,2), dead, at(2,2), alive
        ));

        Assertions.assertThat(grid1.equals(grid2)).isTrue();
    }

    @Test
    void gridsWithSameDispositionOfCellsShouldHaveSameHasgl() {
        final var grid1 = Grid.of(Map.of(
                at(0,0), alive, at(1,0), dead, at(2,0), dead,
                at(0,1), alive, at(1,1), dead, at(2,1), dead,
                at(0,2), dead, at(1,2), dead, at(2,2), alive
        ));

        final var grid2 = Grid.of(Map.of(
                at(0,0), alive, at(1,0), dead, at(2,0), dead,
                at(0,1), alive, at(1,1), dead, at(2,1), dead,
                at(0,2), dead, at(1,2), dead, at(2,2), alive
        ));

        Assertions.assertThat(grid1.hashCode()).isEqualTo(grid2.hashCode());
    }
}