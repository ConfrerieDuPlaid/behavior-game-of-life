Feature: Live cell with more than three live neighbours
  Any live cell with more than three live neighbours dies, as if by overcrowding.

  Scenario: Four live neighbours
    Given the grid
    """
    . . . . .
    . . * . .
    . * * * .
    . . * . .
    . . . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead

  Scenario: Five live neighbours
    Given the grid
    """
    . . . . .
    . . * . .
    . * * * .
    . . * * .
    . . . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead

  Scenario: All neighbours are alive
    Given the grid
    """
    . . . . .
    . * * * .
    . * * * .
    . * * * .
    . . . . .
    """
    When calculating the next generation
    Then the cell in the center should be dead
