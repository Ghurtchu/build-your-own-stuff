package domain

import domain.Position.Index.{ColumnIndex, RowIndex}
import munit.FunSuite

class PositionTest extends FunSuite {

  test("Position.from, where row < 0") {
    val obtained = Position.from(row = -1, col = 1)
    assertEquals(obtained, None)
  }

  test("Position.from, where col < 0") {
    val obtained = Position.from(row = 1, col = -1)
    assertEquals(obtained, None)
  }

  test("Position.from, where row < 0 and col < 0") {
    val obtained = Position.from(row = -1, col = -1)
    assertEquals(obtained, None)
  }

  test("Position.from, where row >= 0 and col >= 0") {
    val obtained = Position.from(row = 1, col = 1)
    assertEquals(obtained, Some(Position(RowIndex(1), ColumnIndex(1))))
  }

}
