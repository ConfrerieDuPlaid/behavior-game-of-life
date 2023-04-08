Feature: Serialization
  Serialize a Grid to a String where :
  - live Cell is star : *
  - dead Cell is dot : .

  Scenario: Grid with only dead Cells
    Given a 4x6 Grid of dead cells
    When serialize the grid
    Then the result should be :
    """
    . . . . . .
    . . . . . .
    . . . . . .
    . . . . . .
    """

  Scenario: Grid with only live Cells
    Given a 3x8 Grid of live cells
    When serialize the grid
    Then the result should be :
    """
    * * * * * * * *
    * * * * * * * *
    * * * * * * * *
    """

  Scenario: Grid with live cell in the middle
    Given a 3x3 Grid of dead cells
    And a live cell in the middle
    When serialize the grid
    Then the result should be :
    """
    . . .
    . * .
    . . .
    """

  Scenario: Grid with dead cell in the midle
    Given a 3x3 Grid of live cells
    And a dead cell in the middle
    When serialize the grid
    Then the result should be :
    """
    * * *
    * . *
    * * *
    """