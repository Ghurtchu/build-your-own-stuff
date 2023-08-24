package domain

import domain.Dataframe.ColumnRetrievalError.{NegativeIndex, TooLargeIndex}
import munit.FunSuite

class DataframeTest extends FunSuite {

  val col1 = Column(
    Header("protein"),
    List(
      Cell(
        Position.from(0, 0).get,
        "10 grams",
      ),
      Cell(
        Position.from(1, 0).get,
        "12 g",
      ),
    ),
  )
  val col2 = Column(
    Header("carbs"),
    List(
      Cell(
        Position.from(0, 1).get,
        "55 g",
      ),
      Cell(
        Position.from(1, 1).get,
        "56 g",
      ),
    ),
  )
  val columns = List(col1, col2)
  val dataframe = Dataframe(columns)

  test("getColumnByIndex") {
    val col1obtained = dataframe.getColumnByIndex(0)
    val col2obtained = dataframe.getColumnByIndex(1)
    val negativeIndexError = dataframe.getColumnByIndex(-1)
    val tooLargeIndex = dataframe.getColumnByIndex(2)

    assertEquals(col1obtained, Right(col1))
    assertEquals(col2obtained, Right(col2))
    assertEquals(negativeIndexError, Left(NegativeIndex))
    assertEquals(negativeIndexError, Left(NegativeIndex))
    assertEquals(tooLargeIndex, Left(TooLargeIndex))
  }

  test("getSliceByIndices") {
    val obtainedSlice1 = dataframe.getSliceByIndices(0, 1)

    val obtainedSlice2 = dataframe.getSliceByIndices(0)
    val expected2 = dataframe.copy(columns.head :: Nil)

    val obtainedSlice3 = dataframe.getSliceByIndices(1)
    val expected3 = dataframe.copy(columns.tail)

    val obtainedSlice4 = dataframe.getSliceByIndices(-1, 0, 1)
    val expected4 = Left(NegativeIndex)

    val obtainedSlice5 = dataframe.getSliceByIndices(0, 1, 2, 3)
    val expected5 = Left(TooLargeIndex)

    assertEquals(obtainedSlice1, Right(dataframe))
    assertEquals(obtainedSlice2, Right(expected2))
    assertEquals(obtainedSlice3, Right(expected3))
    assertEquals(obtainedSlice4, expected4)
    assertEquals(obtainedSlice5, expected5)
  }

  test("getLongestStringForEachColumn") {
    val obtained = dataframe.getMaxStringLengthForEachColumn
    val expected = Map(col1 -> 8, col2 -> 4)

    assertEquals(obtained, expected)
  }

  test("mapHeadersToString") {
    val longestStringForEachColumn = dataframe.getMaxStringLengthForEachColumn
    val obtained = dataframe.mapHeadersToString(longestStringForEachColumn)
    val expected = "protein  carbs"

    assertEquals(obtained, expected)
  }

  test("mapColumnsToRowStrings") {
    val longestStringForEachColumn = dataframe.getMaxStringLengthForEachColumn
    val obtained = dataframe.mapColumnsToRowStrings(longestStringForEachColumn)
    val expected = List(
      "10 grams 55 g",
      "12 g     56 g"
    )

    assertEquals(obtained, expected)
  }
}
