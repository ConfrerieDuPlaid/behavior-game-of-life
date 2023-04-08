package gameoflife;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.*;

public class SerializationStepDefinitions {
    private String serialized = "";
    private Grid grid;

    @Given("a {int}x{int} Grid of {cellState} cells")
    public void a_x_grid_of_cells(int rows, int columns, Cell state) {
        final var cells = new HashMap<Position, Cell>();
        range(0, columns)
                .forEach(x -> range(0, rows)
                .forEach(y -> cells.put(Position.of(x,y).get(), state)));
        this.grid = Grid.of(cells).get();
    }

    @Given("a {cellState} cell in the {middle}")
    public void a_cell_in_the(Cell state, Position center) {
        if(this.grid == null) throw new io.cucumber.java.PendingException("A Grid should already exists in previous steps");

        this.grid = this.grid.withCellAt(state, center);
    }

    @When("serialize the grid")
    public void serialize_the_grid() {
        this.serialized = GridSerializer.serialize(this.grid);
    }
    @Then("the result should be :")
    public void the_result_should_be(String expected) {
        assertEquals(expected, this.serialized);
    }

    @ParameterType("middle")
    public Position middle(String middle){
        return Position.of(this.grid.width/2, this.grid.height/2).get();
    }
}