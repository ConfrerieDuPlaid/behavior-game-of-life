package gameoflife;

import java.util.HashMap;

import static gameoflife.Cell.alive;
import static gameoflife.Cell.dead;

public class Persona {

    public static Grid deadGrid = gridOfDeadCells(4, 6);
    public static Grid liveGrid = gridOfLiveCells(4, 6);


    public static Grid grid(Cell state, int rows, int columns) {
        final var cells = new HashMap<Position, Cell>();
        for (int row = 0; row < rows; row += 1) {
            for (int column = 0; column < columns; column += 1) {
                cells.put(Position.at(column,row), state);
            }
        }
        return Grid.of(cells).get();
    }

    public static Grid gridOfDeadCells(int rows, int columns) {
        return grid(dead, rows, columns);
    }

    public static Grid gridOfLiveCells(int rows, int columns) {
        return grid(alive, rows, columns);
    }
}
