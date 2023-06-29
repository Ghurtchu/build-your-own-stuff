package domain

import munit.FunSuite

class MultiCountResultSpec extends FunSuite {

  test("should return correct .toString representation") {
    val filename = "Users/aghurtchumelia/input.txt"
    val data = MultiCountResult(
      List(
        CountResult(Command.Line, Amount(5)),
        CountResult(Command.Word, Amount(25)),
        CountResult(Command.Character, Amount(125)),
      ),
      filename
    )
    val expected = s"5\t25\t125\t$filename"
    val acutal = data.toString

    assertEquals(acutal, expected)
  }

}
