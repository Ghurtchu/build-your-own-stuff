import domain.LetterFrequency.MapOps
import domain._
trait DetermineLetterFrequency {
  def apply(input: String): LetterFrequency
}

object DetermineLetterFrequency {

  def of: DetermineLetterFrequency =
    (input: String) =>
      input.toSet
        .map(Letter.apply)
        .foldLeft(Map.empty[Letter, Frequency]) { (map, letter) =>
          val frequency = Frequency
            .fromBigInt(input.count(_ == letter.value))
            .getOrElse(Frequency.zero)

          map.updated(letter, frequency)
        }
        .asLetterFrequency

}
