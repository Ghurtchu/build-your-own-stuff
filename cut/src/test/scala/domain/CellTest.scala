package domain

import munit.FunSuite

class CellTest extends FunSuite {

  test("check toString for empty Cell") {
    val emptyCellStr = Cell(Position.zero, "").toString
    assertEquals(emptyCellStr, "")
  }

  test("check toString for non-empty Cell") {
    val nonEmptyCellStr = Cell(Position.zero, "value").toString
    assertEquals(nonEmptyCellStr, "value")
  }

}
