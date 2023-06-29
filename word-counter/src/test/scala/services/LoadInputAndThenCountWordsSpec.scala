package services

import domain.{Amount, Command, CountResult, MultiCountResult}
import munit.FunSuite

import java.nio.file.NoSuchFileException
import scala.util.Try

class LoadInputAndThenCountWordsSpec extends FunSuite {

  test("correctly correctly lines, words and bytes") {
    val loaputdIn = Try {
      """one two three
        |four five six
        |seven eight nine
        |""".stripMargin
    }
    val filepath = "~/imaginary.txt"
    val actual = LoadInputAndThenCountWords.fromFile(filepath, loaputdIn)
    val expected = MultiCountResult(
      results = List(
        CountResult(Command.Line, Amount(3)),
        CountResult(Command.Word, Amount(9)),
        CountResult(Command.Byte, Amount(45)),
      ),
      filepath = filepath,
    )

    assertEquals(actual, Some(expected))
  }

  test("return None since loadInput failed") {
    val input = scala.util.Failure(new NoSuchFileException("file does not exist"))
    val filepath = "~/imaginary.txt"
    val actual = LoadInputAndThenCountWords.fromFile(filepath, input)

    assertEquals(actual, None)
  }

}
