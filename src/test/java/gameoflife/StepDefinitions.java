package gameoflife;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private Grid grid;

    @Given("the grid")
    public void the_grid(String grid) {
        this.grid = GridParser.fromString(grid).get();
    }


    @When("calculating the next generation")
    public void calculating_the_next_generation() {
        this.grid = this.grid.next();
    }


    @Then("the grid should be equal to")
    public void theGridShouldEqualTo(String grid) {
        assertEquals(GridParser.fromString(grid).get(), this.grid);
    }

    @Then("the cell in [{int},{int}] should be {cellState}")
    public void the_cell_in_should_be_dead(int x, int y, Cell state) {
        assertEquals(state, this.grid.cellAt(Position.of(x,y)).get());
    }

    @ParameterType("alive|dead")
    public Cell cellState(String state){
        return Cell.fromString(state).get();
    }

}
