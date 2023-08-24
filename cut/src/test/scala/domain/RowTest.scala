package domain

import munit.FunSuite

class RowTest extends FunSuite {

  test("check toString") {
    val obtained = Row(
      List(
        Cell(Position.from(0, 0).get, "chicken"),
        Cell(Position.from(1, 0).get, "beef"),
        Cell(Position.from(2, 0).get, "veal")
      ),
    ).toString

    assertEquals(obtained, "\tchicken\tbeef\tveal")
  }
}
