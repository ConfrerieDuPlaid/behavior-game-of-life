package gameoflife;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private Grid grid;

    @Given("the grid")
    public void the_grid(String grid) {
        this.grid = Grid.fromString(grid);
    }


    @When("calculating the next generation")
    public void calculating_the_next_generation() {
        this.grid = this.grid.next();
    }


    @Then("the grid should equal to")
    public void theGridShouldEqualTo(String grid) {
        assertEquals(Grid.fromString(grid), this.grid);
    }
}
