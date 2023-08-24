package services

import domain.{Cell, Column, Dataframe, Header, Position}
import munit.FunSuite

class DataframeParserTest extends FunSuite {

  val dataframeStringCsv =
    """protein,carbs,fats,food
      |10,20,30,food1
      |1,2,3,food2
      |""".stripMargin

  val dataframe = Dataframe {
    List(
      Column(
        Header("protein"),
        List(
          Cell(Position.from(0, 0).get, "10"),
          Cell(Position.from(1, 0).get, "1"),
        ),
      ),
      Column(
        Header("carbs"),
        List(
          Cell(Position.from(0, 1).get, "20"),
          Cell(Position.from(1, 1).get, "2"),
        ),
      ),
      Column(
        Header("fats"),
        List(
          Cell(Position.from(0, 2).get, "30"),
          Cell(Position.from(1, 2).get, "3"),
        ),
      ),
      Column(
        Header("food"),
        List(
          Cell(Position.from(0, 3).get, "food1"),
          Cell(Position.from(1, 3).get, "food2"),
        ),
      ),
    )
  }

  test("ofComma") {
    val parser = DataframeParser.ofComma
    val obtained = parser(dataframeStringCsv)

    assertEquals(obtained, Some(dataframe))
  }

  test("ofTab") {
    val parser = DataframeParser.ofTab
    val obtained = parser(dataframeStringCsv.replace(",", "\t"))

    assertEquals(obtained, Some(dataframe))
  }

}
