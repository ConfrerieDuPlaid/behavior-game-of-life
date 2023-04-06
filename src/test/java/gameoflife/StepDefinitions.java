package gameoflife;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;

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
}
