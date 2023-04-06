Feature: Dead cell with three live neighbours
  Any dead cell with exactly three live neighbours becomes a live cell.

  Scenario: Three live neighbours
    Given the grid
    """
    . . .
    . . *
    . * *
    """
    When calculating the next generation
    Then the cell in the center should be alive

  Scenario: Four live neighbours
    Given the grid
    """
    . . .
    * . *
    . * *
    """
    When calculating the next generation
    Then the cell in the center should be dead

  Scenario: Two live neighbours
    Given the grid
    """
    . . .
    * . *
    . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead
