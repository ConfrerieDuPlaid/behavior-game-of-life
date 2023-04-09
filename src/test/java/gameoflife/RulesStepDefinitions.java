package gameoflife;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class RulesStepDefinitions {
    private Generation generation;

    @Given("the grid")
    public void the_grid(String grid) {
        this.generation = new Generation(GridParser.fromString(grid).get());
    }


    @When("calculating the next generation")
    public void calculating_the_next_generation() {
        this.generation = this.generation.next();
    }


    @Then("the grid should be equal to")
    public void the_grid_should_be_equal_to(String grid) {
        assertEquals(GridParser.fromString(grid).get(), this.generation.grid());
    }

    @Then("the cell in [{int},{int}] should be {cellState}")
    public void the_cell_in_should_be_dead(int x, int y, Cell state) {
        assertEquals(state, this.generation.grid().cellAt(Position.of(x,y)).get());
    }

    @Then("the cell in the {center} should be {cellState}")
    public void the_cell_in_should_be(Position position, Cell state) {
        assertEquals(state, this.generation.grid().cellAt(position).get());
    }

    @ParameterType("a?live|dead")
    public Cell cellState(String state){
        return switch (state) {
            case "alive","live" -> Cell.alive;
            case "dead" -> Cell.dead;
            default -> throw new IllegalStateException(state);
        };
    }

    @ParameterType("center")
    public Position center(String center){
        final var grid = generation.grid();
        return Position.of(grid.width/2, grid.height/2);
    }
}
