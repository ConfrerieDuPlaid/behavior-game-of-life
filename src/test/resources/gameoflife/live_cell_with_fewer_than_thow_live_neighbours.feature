Feature: Live cell with fewer than two live neighbours
  Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.

  Scenario: A live cell alone
    Given the grid
    """
    . . .
    . * .
    . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead

  Scenario: one live cell neighbour
    Given the grid
    """
    . . .
    . * *
    . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead

  Scenario: many live cells but not neighbours
    Given the grid
    """
    * . . . . .
    . . . . * .
    * . . . . .
    . . . * . .
    """
    When calculating the next generation
    Then the grid should be equal to
    """
    . . . . . .
    . . . . . .
    . . . . . .
    . . . . . .
    """