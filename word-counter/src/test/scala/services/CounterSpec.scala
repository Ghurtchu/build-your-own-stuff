package services

import domain.Command
import munit.FunSuite

class CounterSpec extends FunSuite {

  test("count correct amount of words") {
    val cmd = Command.Word
    val counter = Counter fromCommand cmd
    val input =
      """one two three
        |for five six
        |seven eight nine
        |""".stripMargin
    val actual = counter.count(input)
    val expected: BigInt = 9

    assertEquals(actual.amount.value, expected)
  }

  test("count correct amount of lines") {
    val cmd = Command.Line
    val counter = Counter fromCommand cmd
    val input =
      """one two three
        |for five six
        |seven eight nine
        |""".stripMargin
    val actual = counter.count(input)
    val expected: BigInt = 3

    assertEquals(actual.amount.value, expected)
  }

  test("count correct amount of characters") {
    val cmd = Command.Character
    val counter = Counter fromCommand cmd
    val input =
      """one one one
        |two two two
        |""".stripMargin
    val actual = counter.count(input)
    val chars = 22
    val newLines = 2
    val expected: BigInt = chars + newLines

    assertEquals(actual.amount.value, expected)
  }

  test("count correct amount of bytes") {
    val cmd = Command.Byte
    val counter = Counter fromCommand cmd
    val input =
      """hello, there
        |hi, there
        |""".stripMargin
    val actual = counter.count(input)
    val expected: BigInt = 23

    assertEquals(actual.amount.value, expected)
  }
}
