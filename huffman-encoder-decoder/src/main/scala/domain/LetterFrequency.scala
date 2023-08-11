package domain

final case class LetterFrequency(value: Map[Letter, Frequency]) extends AnyVal

object LetterFrequency {

  implicit class MapOps(private val self: Map[Letter, Frequency]) extends AnyVal {
    def asLetterFrequency: LetterFrequency = LetterFrequency(self)
  }
}
