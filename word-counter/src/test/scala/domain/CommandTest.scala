package domain

import munit.FunSuite

class CommandTest extends FunSuite {

  test("should return Some(Byte) if input equals 'c'") {
    assertEquals(Command.fromString("c"), Some(Command.Byte))
  }

  test("should return Some(Character) if input equals 'm'") {
    assertEquals(Command.fromString("m"), Some(Command.Character))
  }

  test("should return Some(Word) if input equals 'w'") {
    assertEquals(Command.fromString("w"), Some(Command.Word))
  }

  test("should return Some(Line) if input equals 'l'") {
    assertEquals(Command.fromString("l"), Some(Command.Line))
  }

  test("should return None if input is not in ['c', 'm', 'w', 'l']") {
    assertEquals(Command.fromString("a"), None)
  }

  test("return correct toString for Byte") {
    assertEquals(Command.fromString("c").map(_.toString), Some("byte"))
  }

  test("return correct toString for Character") {
    assertEquals(Command.fromString("m").map(_.toString), Some("character"))
  }

  test("return correct toString for Word") {
    assertEquals(Command.fromString("w").map(_.toString), Some("word"))
  }

  test("return correct toString for Line") {
    assertEquals(Command.fromString("l").map(_.toString), Some("line"))
  }
}
