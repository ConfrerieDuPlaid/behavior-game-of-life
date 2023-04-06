Feature: Move than three live neighbours
  Any live cell with more than three live neighbours dies, as if by overcrowding.

  Scenario: Five live neighbours
    Given the grid
    """
    . * .
    * * *
    . * *
    """
    When calculating the next generation
    Then the grid should be equal to
    """
    . * .
    * . *
    . * *
    """
