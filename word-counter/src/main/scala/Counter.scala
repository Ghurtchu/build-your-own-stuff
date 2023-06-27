trait Counter {
  def count(input: String): Option[CountResult]
}

object Counter {
  def apply(userOption: UserOption): Counter = (input: String) =>
    CountResult(
      userOption = userOption,
      amount = userOption match {
        case UserOption.Byte => input.getBytes.sum
        case UserOption.Character => input.length
        case UserOption.Word => input.split(" ").length
        case UserOption.Line => input.split("\n").length
      },
    )

}
