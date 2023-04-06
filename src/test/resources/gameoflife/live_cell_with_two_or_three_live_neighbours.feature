Feature: Two or three live neighbours
  Any live cell with two or three live neighbours lives on to the next generation.

  Scenario: Two live neighbours
    Given the grid
    """
    . . . . .
    . . * . .
    . . * . .
    . . * . .
    . . . . .
    """
    When calculating the next generation
    Then the cell in the center should be alive

  Scenario: Two live neighbours on edge
    Given the grid
    """
    . . . . .
    . . . . *
    . . . . *
    . . . . *
    . . . . *
    """
    When calculating the next generation
    Then the cell in [4,2] should be alive
    And the cell in [4,3] should be alive

  Scenario: Three live neighbours
    Given the grid
    """
    . . . . .
    . * . * .
    . . * . .
    . . * . .
    . . . . .
    """
    When calculating the next generation
    Then the cell in the center should be alive

  Scenario: Three live neighbours on edge
    Given the grid
    """
    . . . . *
    . . . * *
    . . . . *
    . . * . .
    . * * * .
    """
    When calculating the next generation
    Then the cell in [4,2] should be alive
    And the cell in [2,4] should be alive