Feature: Fewer than two live neighbours
  Any live cell with fewer than two live neighbours dies

  Scenario: Grid with unique cell
    Given the grid
    """
    . . .
    . * .
    . . .
    """
    When calculating the next generation
    Then the grid should be equal to
    """
    . . .
    . . .
    . . .
    """