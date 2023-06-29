package domain

import munit.FunSuite

class CountResultSpec extends FunSuite {

  test("return correct toString for plural Character case") {
    assertEquals(CountResult(Command.Character, Amount(500)).toString, "500 characters")
  }

  test("return correct toString for plural Line case") {
    assertEquals(CountResult(Command.Line, Amount(500)).toString, "500 lines")
  }

  test("return correct toString for plural Word case") {
    assertEquals(CountResult(Command.Word, Amount(500)).toString, "500 words")
  }

  test("return correct toString for plural Byte case") {
    assertEquals(CountResult(Command.Byte, Amount(500)).toString, "500 bytes")
  }

  test("return correct toString for singular Character case") {
    assertEquals(CountResult(Command.Character, Amount(1)).toString, "1 character")
  }

  test("return correct toString for singular Line case") {
    assertEquals(CountResult(Command.Line, Amount(1)).toString, "1 line")
  }

  test("return correct toString for singular Word case") {
    assertEquals(CountResult(Command.Word, Amount(1)).toString, "1 word")
  }

  test("return correct toString for singular Byte case") {
    assertEquals(CountResult(Command.Byte, Amount(1)).toString, "1 byte")
  }

}
