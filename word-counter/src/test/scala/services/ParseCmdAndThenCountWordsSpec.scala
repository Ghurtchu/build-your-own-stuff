package services

import domain.{Amount, Command, CountResult}
import munit.FunSuite
import services.ParseCmdAndThenCountWords.Error.UnknownCommand

import scala.util.Try

class ParseCmdAndThenCountWordsSpec extends FunSuite {

  test("correctly count lines") {
    val loadInput = Try {
      """one two three
        |four five six
        |seven eight nine
        |""".stripMargin
    }
    val filepath = "~/imaginary.txt"
    val actual = ParseCmdAndThenCountWords.fromFile(filepath)("l", loadInput)
    val expected = CountResult(Command.Line, Amount(3))

    assertEquals(actual, Right(expected))
  }

  test("return Left(UnkownCommand) since command is unknown") {
    val cmd = "gibberish"
    val actual = ParseCmdAndThenCountWords.fromStdIn(cmd, Try(""))

    assertEquals(actual, Left(UnknownCommand(cmd)))
  }
}
