package domain

import munit.FunSuite

class ColumnTest extends FunSuite {

  test("check toString for Column") {
    val header = Header("protein")
    val cells = List(
      Cell(
        Position.from(1, 1).get,
        "15 g"
      ),
      Cell(
        Position.from(2, 1).get,
        "10 g"
      ),
      Cell(
        Position.from(3, 1).get,
        "5 g"
      ),
    )
    val column = Column(header, cells)
    val toStringExpected =
      """protein
        |15 g
        |10 g
        |5 g""".stripMargin

    assertEquals(column.toString, toStringExpected)
  }

}
