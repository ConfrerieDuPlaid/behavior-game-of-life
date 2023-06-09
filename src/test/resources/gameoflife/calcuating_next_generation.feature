Feature: Calculating next generation
  The state of a cell on a generation is calculated according to its state and the state of the neighboring cells on the previous generation.
  The calculation of the new state of a cell must not take into account the state of the current generation of neighboring cells, only the previous generation.

  Scenario: dead cell becomes a live cell
    Given the grid
    """
    ........
    ....*...
    ...**...
    ........
    """
    When calculating the next generation
    Then the grid should be equal to
    """
    ........
    ...**...
    ...**...
    ........
    """

  Scenario: big cross of live cells
    Given the grid
    """
    ..**....
    .****...
    ..**....
    ........
    """
    When calculating the next generation
    Then the grid should be equal to
    """
    .*..*...
    .*..*...
    .*..*...
    ........
    """

  Scenario: big cross of live cells on two generations
    Given the grid
    """
    ..**....
    .****...
    ..**....
    ........
    """
    When calculating the next generation
    When calculating the next generation
    Then the grid should be equal to
    """
    ........
    ******..
    ........
    ........
    """